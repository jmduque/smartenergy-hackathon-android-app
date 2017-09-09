package com.energolabs.evergo.ui.views

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.IDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ViewPortHandler
import java.util.*

/**
 * Created by Jose Duque on 12/22/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class EnergoLineChartRenderer(
        chart: LineDataProvider,
        animator: ChartAnimator,
        viewPortHandler: ViewPortHandler
) : LineChartRenderer(chart, animator, viewPortHandler) {

    /**
     * Cache of highlighted entries
     */
    private val highlightedEntries = HashSet<Entry>()

    /**
     * cache for the circle bitmaps of all datasets
     */
    private val mImageCaches = HashMap<IDataSet<*>, DataSetImageCache>()

    /**
     * buffer for drawing the circles
     */
    private val mCirclesBuffer = FloatArray(2)

    override fun drawCircles(c: Canvas) {
        mRenderPaint.style = Paint.Style.FILL

        val phaseY = mAnimator.phaseY

        mCirclesBuffer[0] = 0f
        mCirclesBuffer[1] = 0f

        val dataSets = mChart.lineData.dataSets

        for (i in dataSets.indices) {

            val dataSet = dataSets[i]

            if (!dataSet.isVisible || !dataSet.isDrawCirclesEnabled ||
                    dataSet.entryCount == 0)
                continue

            mCirclePaintInner.color = dataSet.circleHoleColor

            val trans = mChart.getTransformer(dataSet.axisDependency)

            mXBounds.set(mChart, dataSet)

            val circleRadius = dataSet.circleRadius
            val circleHoleRadius = dataSet.circleHoleRadius
            val drawCircleHole = dataSet.isDrawCircleHoleEnabled &&
                    circleHoleRadius < circleRadius &&
                    circleHoleRadius > 0f
            val drawTransparentCircleHole = drawCircleHole && dataSet.circleHoleColor == ColorTemplate.COLOR_NONE

            val imageCache: DataSetImageCache

            if (mImageCaches.containsKey(dataSet)) {
                imageCache = mImageCaches[dataSet] ?: return
            } else {
                imageCache = DataSetImageCache()
                mImageCaches.put(dataSet, imageCache)
            }

            val changeRequired = imageCache.init(dataSet)

            // only fill the cache with new bitmaps if a change is required
            if (changeRequired) {
                imageCache.fill(dataSet, drawCircleHole, drawTransparentCircleHole)
            }

            val boundsRangeCount = mXBounds.range + mXBounds.min

            for (j in mXBounds.min..boundsRangeCount) {
                val e = dataSet.getEntryForIndex(j)

                if (!highlightedEntries.contains(e)) continue
                if (e == null) break

                mCirclesBuffer[0] = e.x
                mCirclesBuffer[1] = e.y * phaseY

                trans.pointValuesToPixel(mCirclesBuffer)

                if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0]))
                    break

                if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) || !mViewPortHandler.isInBoundsY(mCirclesBuffer[1]))
                    continue

                val circleBitmap = imageCache.getBitmap(j)

                if (circleBitmap != null) {
                    c.drawBitmap(circleBitmap, mCirclesBuffer[0] - circleRadius, mCirclesBuffer[1] - circleRadius, mRenderPaint)
                }
            }
        }
    }

    override fun drawHighlighted(c: Canvas, indices: Array<Highlight>) {
        highlightedEntries.clear()
        val lineData = mChart.lineData

        for (high in indices) {

            val set = lineData.getDataSetByIndex(high.dataSetIndex)

            if (set == null || !set.isHighlightEnabled)
                continue

            val e = set.getEntryForXValue(high.x, high.y)
            if (!isInBoundsX(e, set))
                continue

            highlightedEntries.add(e)
            val pix = mChart.getTransformer(set.axisDependency).getPixelForValues(e.x, e.y * mAnimator
                    .phaseY)

            high.setDraw(pix.x.toFloat(), pix.y.toFloat())

            // draw the lines
            drawHighlightLines(c, pix.x.toFloat(), pix.y.toFloat(), set)
        }
    }

    override fun drawValues(c: Canvas) {

        if (isDrawingValuesAllowed(mChart)) {

            val dataSets = mChart.lineData.dataSets

            for (i in dataSets.indices) {

                val dataSet = dataSets[i]

                if (!shouldDrawValues(dataSet))
                    continue

                // apply the text-styling defined by the DataSet
                applyValueTextStyle(dataSet)

                val trans = mChart.getTransformer(dataSet.axisDependency)

                // make sure the values do not interfear with the circles
                var valOffset = (dataSet.circleRadius * 1.75f).toInt()

                if (!dataSet.isDrawCirclesEnabled)
                    valOffset /= 2

                mXBounds.set(mChart, dataSet)

                val positions = trans.generateTransformedValuesLine(dataSet, mAnimator.phaseX, mAnimator
                        .phaseY, mXBounds.min, mXBounds.max)

                var j = 0
                while (j < positions.size) {

                    val x = positions[j]
                    val y = positions[j + 1]

                    if (!mViewPortHandler.isInBoundsRight(x))
                        break

                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y)) {
                        j += 2
                        continue
                    }

                    val entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min)
                    if (entry == null) {
                        j += 2
                        continue
                    }

                    if (!highlightedEntries.contains(entry)) {
                        j += 2
                        continue
                    }

                    drawValue(
                            c,
                            dataSet.valueFormatter,
                            entry.y,
                            entry,
                            i,
                            x,
                            y - valOffset,
                            dataSet.getValueTextColor(j / 2)
                    )
                    j += 2
                }
            }
        }
    }

    private inner class DataSetImageCache {

        private val mCirclePathBuffer = Path()

        private var circleBitmaps: Array<Bitmap?>? = null

        /**
         * Sets up the cache, returns true if a change of cache was required.

         * @param set
         * *
         * @return
         */
        fun init(set: ILineDataSet): Boolean {

            val size = set.circleColorCount
            var changeRequired = false

            if (circleBitmaps == null) {
                circleBitmaps = arrayOfNulls<Bitmap>(size)
                changeRequired = true
            } else if (circleBitmaps?.size != size) {
                circleBitmaps = arrayOfNulls<Bitmap>(size)
                changeRequired = true
            }

            return changeRequired
        }

        /**
         * Fills the cache with bitmaps for the given dataset.

         * @param set
         * *
         * @param drawCircleHole
         * *
         * @param drawTransparentCircleHole
         */
        fun fill(set: ILineDataSet, drawCircleHole: Boolean, drawTransparentCircleHole: Boolean) {

            val colorCount = set.circleColorCount
            val circleRadius = set.circleRadius
            val circleHoleRadius = set.circleHoleRadius

            for (i in 0..colorCount - 1) {

                val conf = Bitmap.Config.ARGB_4444
                val circleBitmap = Bitmap.createBitmap((circleRadius * 2.1).toInt(), (circleRadius * 2.1).toInt(), conf)

                val canvas = Canvas(circleBitmap)
                circleBitmaps?.set(i, circleBitmap) ?: return
                mRenderPaint.color = set.getCircleColor(i)

                if (drawTransparentCircleHole) {
                    // Begin pathSquaredAndResize for circle with hole
                    mCirclePathBuffer.reset()

                    mCirclePathBuffer.addCircle(
                            circleRadius,
                            circleRadius,
                            circleRadius,
                            Path.Direction.CW)

                    // Cut hole in pathSquaredAndResize
                    mCirclePathBuffer.addCircle(
                            circleRadius,
                            circleRadius,
                            circleHoleRadius,
                            Path.Direction.CCW)

                    // Fill in-between
                    canvas.drawPath(mCirclePathBuffer, mRenderPaint)
                } else {

                    canvas.drawCircle(
                            circleRadius,
                            circleRadius,
                            circleRadius,
                            mRenderPaint)

                    if (drawCircleHole) {
                        canvas.drawCircle(
                                circleRadius,
                                circleRadius,
                                circleHoleRadius,
                                mCirclePaintInner)
                    }
                }
            }
        }

        /**
         * Returns the cached Bitmap at the given index.

         * @param index
         * *
         * @return
         */
        fun getBitmap(index: Int): Bitmap? {
            return circleBitmaps?.get(
                    index.rem(circleBitmaps?.size ?: return null)
            )
        }
    }

}

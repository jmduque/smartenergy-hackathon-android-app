package com.energolabs.evergo.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.os.AsyncTask
import android.text.TextUtils
import android.util.Base64
import android.widget.ImageView
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.AsyncTaskController
import java.io.*
import java.util.*

class BitmapUtils private constructor() {

    fun decodeToSetBitmapPictureAsync(base64image: String,
                                      imageView: ImageView?): DecodePictureAsync? {
        if (TextUtils.isEmpty(base64image)) {
            return null
        }

        if (imageView == null) {
            return null
        }

        val result = DecodePictureAsync(
                imageView,
                true
        )
        val params = arrayOf<Any>(base64image)
        AsyncTaskController.startTask(result, params)
        return result
    }

    fun decodePictureAsync(
            base64image: String,
            imageView: ImageView?,
            width: Int,
            height: Int,
            addToMemory: Boolean
    ): DecodePictureAsync? {
        if (TextUtils.isEmpty(base64image)) {
            return null
        }

        if (imageView == null) {
            return null
        }

        val result = DecodePictureAsync(
                width,
                height,
                imageView
        )

        val params = arrayOf<Any>(base64image)
        AsyncTaskController.startTask(result, params)
        return result
    }

    fun computeSampleSize(options: BitmapFactory.Options,
                          minSideLength: Int, maxNumOfPixels: Int): Int {
        val initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels)
        var roundedSize: Int
        if (initialSize <= 8) {
            roundedSize = 1
            while (roundedSize < initialSize) {
                roundedSize = roundedSize shl 1
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8
        }
        return roundedSize
    }

    fun computeInitialSampleSize(options: BitmapFactory.Options,
                                 minSideLength: Int, maxNumOfPixels: Int): Int {
        val w = options.outWidth.toDouble()
        val h = options.outHeight.toDouble()
        val lowerBound = if (maxNumOfPixels == -1)
            1
        else
            Math.ceil(Math
                    .sqrt(w * h / maxNumOfPixels)).toInt()
        val upperBound = if (minSideLength == -1)
            128
        else
            Math.min(
                    Math.floor(w / minSideLength), Math.floor(h / minSideLength)).toInt()
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound
        }
        if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1
        } else if (minSideLength == -1) {
            return lowerBound
        } else {
            return upperBound
        }
    }

    inner class DecodePictureAsync : AsyncTask<Any, Void, Bitmap> {

        internal var decodePictureCallBackListener: DecodePictureCallBackListener? = null
        internal var iv_content_pic: ImageView
        internal var width: Int = 0
        internal var height: Int = 0
        internal var setToImageBitmap = false

        constructor(
                imageview: ImageView,
                decodePictureCallBackListener: DecodePictureCallBackListener?
        ) {
            this.iv_content_pic = imageview
            this.decodePictureCallBackListener = decodePictureCallBackListener
        }

        constructor(
                imageview: ImageView,
                setToImageBitmap: Boolean
        ) : this(imageview, null) {
            this.setToImageBitmap = setToImageBitmap
        }

        constructor(
                width: Int,
                height: Int,
                imageview: ImageView
        ) {
            this.width = width
            this.height = height
            this.iv_content_pic = imageview
        }

        override fun doInBackground(vararg params: Any): Bitmap? {
            val base64image = params[0] as String
            if (TextUtils.isEmpty(base64image)) {
                return null
            }

            val data = Base64.decode(base64image, Base64.DEFAULT)
            val bitmap: Bitmap?
            if (width != 0 && height != 0) {
                val option = BitmapFactory.Options()
                option.inJustDecodeBounds = true
                BitmapFactory.decodeByteArray(data, 0, data.size, option)
                option.inSampleSize = computeSampleSize(option, -1, width * height)
                option.inJustDecodeBounds = false

                val decodeBitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.size, option) ?: return null
                bitmap = Bitmap.createScaledBitmap(decodeBitmap, width, height,
                        true)
            } else {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            }

            if (bitmap == null) {
                return null
            }

            val result = getSquaredBitmap(bitmap)
            if (bitmap != result) {
                bitmap.recycle()
            }

            return result
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result == null) {
                iv_content_pic.setImageResource(R.drawable.ic_default_avatar_boy)
                if (decodePictureCallBackListener != null) {
                    decodePictureCallBackListener?.decodeFinish()
                }
                return
            }

            if (setToImageBitmap) {
                iv_content_pic.setImageBitmap(result)
            } else {
                val drawable = BitmapDrawable(
                        iv_content_pic.resources, result)
                iv_content_pic.background = drawable
            }


            if (decodePictureCallBackListener != null) {
                decodePictureCallBackListener?.decodeFinish()
            }
        }

        override fun onCancelled(result: Bitmap?) {
            super.onCancelled(result)
            if (result == null) {
                return
            }
            if (!result.isRecycled) {
                result.recycle()
            }
        }

    }

    interface DecodePictureCallBackListener {
        fun decodeFinish()
    }

    companion object {

        // Preview Settings
        val PARAM_MAX_HEIGHTWIDTH = 150

        private val instance: BitmapUtils? = BitmapUtils()

        private fun resizeBitmap(bitmap: Bitmap, newSize: Int): Bitmap {
            val width = bitmap.width
            val height = bitmap.height

            var newWidth = 0
            var newHeight = 0

            if (width > height) {
                newWidth = newSize
                newHeight = newSize * height / width
            } else if (width < height) {
                newHeight = newSize
                newWidth = newSize * width / height
            } else if (width == height) {
                newHeight = newSize
                newWidth = newSize
            }

            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height

            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)

            val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                    matrix, true)

            return resizedBitmap
        }

        /**
         * @param filePath
         * *
         * @return
         */
        @JvmOverloads fun createVideoThumbnailByTime(
                filePath: String,
                scale: Int,
                maxDim: Int = 2 * PARAM_MAX_HEIGHTWIDTH
        ): Bitmap? {
            var bitmap: Bitmap? = null
            val retriever = MediaMetadataRetriever()
            try {// MODE_CAPTURE_FRAME_ONLY
                retriever.setDataSource(filePath)
                val timeString = retriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                val time = (java.lang.Double.parseDouble(timeString) * 1000).toLong()
                bitmap = retriever.getFrameAtTime(time * scale / 1000,
                        MediaMetadataRetriever.OPTION_CLOSEST_SYNC) // éŽ¸å¤Žîž…æ£°æˆ¦æš±æ�´ï¸½ç˜®æ¸šå¬®ï¿½éŽ·â•�æŠš
            } catch (ignored: Exception) {
            } finally {
                try {
                    retriever.release()
                } catch (ex: RuntimeException) {
                    return null
                }

            }
            if (bitmap != null)
                return resizeBitmap(bitmap, maxDim)
            else
                return null
        }

        fun calculateInSize(options: BitmapFactory.Options, maxDim: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > maxDim || width > maxDim) {

                // Calculate ratios of height and width to requested height and
                // width
                val heightRatio = Math.round(height.toFloat() / maxDim.toFloat())
                val widthRatio = Math.round(width.toFloat() / maxDim.toFloat())

                // Choose the smallest ratio as inSampleSize value, this will
                // guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }

            return inSampleSize
        }

        fun decodeSampledBitmapFromPath(filePath: String): Bitmap? {
            return decodeSquaredBitmapFromPath(
                    filePath,
                    PARAM_MAX_HEIGHTWIDTH
            )
        }

        fun decodeSquaredBitmapFromPath(filePath: String, maxDim: Int): Bitmap? {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, options)
            // BitmapFactory.decodeStream(is, outPadding, opts);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSize(options, maxDim)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false

            val myBitmap = BitmapFactory.decodeFile(filePath, options)

            try {
                val exif = ExifInterface(filePath)
                val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, 1)

                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                }

                val result = getSquaredBitmap(myBitmap, matrix, true)
                if (myBitmap != result) {
                    myBitmap.recycle()
                }
                return result
            } catch (ignored: Exception) {

            }

            return myBitmap
        }

        fun decodeBitmapFromPath(filePath: String): Bitmap {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(filePath, options)
        }

        /**
         * bitmap convert to base64

         * @param bitmap
         * *
         * @return
         */
        fun bitmapToBase64(bitmap: Bitmap?): String? {
            var result: String? = null
            var baos: ByteArrayOutputStream? = null
            try {
                if (bitmap != null) {
                    baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                    baos.flush()
                    baos.close()

                    val bitmapBytes = baos.toByteArray()
                    result = Base64.encodeToString(
                            bitmapBytes,
                            Base64.NO_WRAP
                    )
                }
            } catch (ignored: IOException) {
            } finally {
                try {
                    if (baos != null) {
                        baos.flush()
                        baos.close()
                    }
                } catch (ignored: IOException) {
                }

            }
            return result
        }

        /**
         * base64 convert to bitmap

         * @param base64Data
         * *
         * @return
         */
        fun base64ToBitmap(base64Data: String): Bitmap? {
            if (TextUtils.isEmpty(base64Data)) {
                return null
            }

            var bytes: ByteArray? = null

            try {
                bytes = android.util.Base64.decode(base64Data,
                        android.util.Base64.DEFAULT)
            } catch (e: Exception) {
                return null
            }

            val myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes?.size ?: return null)
            val result = getSquaredBitmap(myBitmap) ?: return null

            if (result != myBitmap) {
                myBitmap.recycle()
            }

            return result
        }

        /**
         * @param path - photoPath
         * *
         * @author will.Wu Read the photo exif rotation Angle of the information If
         * * the Angle is greater than zero, rotating the corresponding point
         * * of view, and replacing the original image
         */
        @Throws(OutOfMemoryError::class)
        fun setImageDegree(path: String) {
            if (TextUtils.isEmpty(path))
                return

            var degree = 0
            try {
                val exifInterface = ExifInterface(path)
                val orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL)
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            } catch (ignored: IOException) {
            }

            if (degree != 0) {
                // Get no rotation of the Bitmap
                val opts = BitmapFactory.Options()
                opts.inJustDecodeBounds = false
                opts.inPurgeable = true
                opts.inInputShareable = true
                opts.inSampleSize = 4
                val srcBitmap = BitmapFactory.decodeFile(path, opts)
                val matrix = Matrix()
                matrix.postRotate(degree.toFloat())
                val rotatedBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
                        srcBitmap.width, srcBitmap.height, matrix, true)
                try {
                    saveMyBitmap(path, rotatedBitmap)
                } catch (ignored: IOException) {
                }

                rotatedBitmap.recycle()
                srcBitmap.recycle()
            }
        }

        @JvmOverloads fun getSquaredBitmap(bitmap: Bitmap?, m: Matrix? = null,
                                           filter: Boolean = false): Bitmap? {
            if (bitmap == null) {
                return null
            }

            if (bitmap.isRecycled) {
                return null
            }

            var width = bitmap.width
            var height = bitmap.height
            var x = 0
            var y = 0

            if (width <= 0) {
                return null
            }

            if (height <= 0) {
                return null
            }

            if (width < height) {
                y = (height - width) / 2
                height = width
            } else {
                x = (width - height) / 2
                width = height
            }

            return Bitmap.createBitmap(bitmap, x, y, width, height, m, filter)
        }

        /**
         * Save Bitmap To File

         * @param filePath
         * *
         * @param saveBitmap
         * *
         * @throws IOException
         * *
         * @author will.Wu
         */
        @Throws(IOException::class)
        fun saveMyBitmap(filePath: String, saveBitmap: Bitmap) {
            val f = File(filePath)
            f.createNewFile()
            var fOut: FileOutputStream? = null
            try {
                fOut = FileOutputStream(f)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            try {
                fOut?.flush()
            } catch (ignored: IOException) {
            }

            try {
                fOut?.close()
            } catch (ignored: IOException) {
            }

        }

        fun getMaxnSizeBitmapFromPath(filePath: String): Bitmap? {
            if (TextUtils.isEmpty(filePath)) {
                return null
            }
            val maxSize = 700
            val newOpts = BitmapFactory.Options()
            newOpts.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, newOpts)

            newOpts.inJustDecodeBounds = false
            val w = newOpts.outWidth.toDouble()
            val h = newOpts.outHeight.toDouble()
            if (w < maxSize && h < maxSize) {
                return null
            }
            var hh = 0
            var ww = 0
            if (w > h) {
                val hOffSet = h / w
                ww = maxSize
                hh = (ww * hOffSet).toInt()
            } else if (h > w) {
                val wOffSet = w / h
                hh = maxSize
                ww = (hh * wOffSet).toInt()
            }
            var be = 1
            if (w > h && w > ww) {
                be = newOpts.outWidth / ww
            } else if (w < h && h > hh) {
                be = newOpts.outHeight / hh
            }
            if (be <= 0)
                be = 1
            newOpts.inSampleSize = be
            return BitmapFactory.decodeFile(filePath, newOpts)
        }

        /**
         * @param a -
         * *          Array<Bitmpa> - Array of bitmaps which are require to be in the overlaping bitmap
         * *          - Maximum bitmaps in result bitmap is 4
         * *
         * @return - Bitmap
         * *
         * @author Jose Duque
        </Bitmpa> */
        fun generateOverlapingGroupChatAvatar(a: ArrayList<Bitmap>): Bitmap? {
            val CHILD_BITMAP_WIDTH = 60
            val CHILD_BITMAP_HEIGHT = 60

            var width = 0
            var height = 0

            val size = a.size
            // RESIZE CHILD BITMAPS
            for (i in 0..size - 1) {
                val resizedBitmap = getResizedBitmap(a[i], CHILD_BITMAP_WIDTH, CHILD_BITMAP_HEIGHT)
                a.removeAt(i)
                a.add(i, resizedBitmap)
            }

            var combineBitmap: Bitmap? = null
            if (size == 2) {
                width = CHILD_BITMAP_WIDTH + CHILD_BITMAP_WIDTH / 2 + 24
                height = CHILD_BITMAP_HEIGHT + CHILD_BITMAP_HEIGHT / 2 + 24
            } else if (size == 3) {
                width = CHILD_BITMAP_WIDTH + CHILD_BITMAP_WIDTH / 2 + 40
                height = CHILD_BITMAP_HEIGHT + CHILD_BITMAP_HEIGHT / 2 + 40
            } else if (size == 4) {
                width = CHILD_BITMAP_WIDTH + CHILD_BITMAP_WIDTH / 2 + 48
                height = CHILD_BITMAP_HEIGHT + CHILD_BITMAP_HEIGHT / 2 + 48
            } else {
                width = CHILD_BITMAP_WIDTH + CHILD_BITMAP_WIDTH / 2 + 48
                height = CHILD_BITMAP_HEIGHT + CHILD_BITMAP_HEIGHT / 2 + 48
            }

            combineBitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888)
            val comboImage = Canvas(combineBitmap)


            if (size == 2) {
                comboImage.drawBitmap(a[0], (CHILD_BITMAP_WIDTH / 2 + 16).toFloat(), (CHILD_BITMAP_HEIGHT / 2 + 8).toFloat(), null)//RIGHT BOTTOM
                comboImage.drawBitmap(a[1], 8f, 8f, null)//TOP LEFT
            } else if (size == 3) {
                comboImage.drawBitmap(a[0], (CHILD_BITMAP_WIDTH / 2 + 28).toFloat(), (CHILD_BITMAP_HEIGHT / 2 + 20).toFloat(), null)//RIGHT BOTTOM
                comboImage.drawBitmap(a[1], 4f, (CHILD_BITMAP_HEIGHT / 2 + 20).toFloat(), null)//LEFT BOTTOM
                comboImage.drawBitmap(a[2], (CHILD_BITMAP_WIDTH / 4 + 16).toFloat(), 0f, null)// TOP CENTER
            } else if (size == 4) {
                comboImage.drawBitmap(a[0], (CHILD_BITMAP_WIDTH / 2 + 32).toFloat(), (CHILD_BITMAP_HEIGHT / 2 + 24).toFloat(), null)//RIGHT BOTTOM
                comboImage.drawBitmap(a[1], 8f, (CHILD_BITMAP_HEIGHT / 2 + 24).toFloat(), null)//LEFT BOTTOM
                comboImage.drawBitmap(a[2], (CHILD_BITMAP_WIDTH / 2 + 32).toFloat(), 8f, null)// TOP RIGHT
                comboImage.drawBitmap(a[3], 8f, 8f, null)// TOP lEFT
            } else {
                comboImage.drawBitmap(a[0], (CHILD_BITMAP_WIDTH / 2 + 32).toFloat(), (CHILD_BITMAP_HEIGHT / 2 + 24).toFloat(), null)//RIGHT BOTTOM
                comboImage.drawBitmap(a[1], 8f, (CHILD_BITMAP_HEIGHT / 2 + 24).toFloat(), null)//LEFT BOTTOM
                comboImage.drawBitmap(a[2], (CHILD_BITMAP_WIDTH / 2 + 32).toFloat(), 8f, null)// TOP RIGHT
                comboImage.drawBitmap(a[a.size - 1], 8f, 8f, null)// TOP lEFT
            }
            return combineBitmap
        }

        /**
         * @param bm
         * *
         * @param newWidth
         * *
         * @param newHeight
         * *
         * @return Bitmap
         * *
         * @author Jose Duque
         */
        fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)

            // "RECREATE" THE NEW BITMAP
            if (!bm.isRecycled) {
                val resizedBitmap = Bitmap.createBitmap(
                        bm, 0, 0, width, height, matrix, false)
                bm.recycle()
                return generateCirlceBitmapWithWhiteBorder(resizedBitmap)
            }
            return generateCirlceBitmapWithWhiteBorder(bm)
        }

        /**
         * @param bitmap
         * *
         * @return bitmap
         * *
         * @author Jose Duque
         */
        fun generateCirlceBitmapWithWhiteBorder(bitmap: Bitmap): Bitmap {

            val w = bitmap.width
            val h = bitmap.height

            val radius = Math.min(h / 2, w / 2)
            val output = Bitmap.createBitmap(w + 8, h + 8, Bitmap.Config.ARGB_8888)

            val p = Paint()
            p.isAntiAlias = true

            val c = Canvas(output)
            c.drawARGB(0, 0, 0, 0)
            p.style = Paint.Style.FILL

            c.drawCircle((w / 2 + 4).toFloat(), (h / 2 + 4).toFloat(), radius.toFloat(), p)

            p.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            c.drawBitmap(bitmap, 4f, 4f, p)
            p.xfermode = null
            p.style = Paint.Style.STROKE
            p.color = Color.WHITE
            p.strokeWidth = 3f
            c.drawCircle((w / 2 + 4).toFloat(), (h / 2 + 4).toFloat(), radius.toFloat(), p)

            return output
        }
    }


}
/**
 * @param filePath
 * *
 * @param scale    the position in the video from 0 to 1000 (that means that a
 * *                 value of 500 will return the mid-video frame)
 * *
 * @return
 */
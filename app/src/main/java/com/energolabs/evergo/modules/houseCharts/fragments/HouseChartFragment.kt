package com.energolabs.evergo.modules.houseCharts.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.energyWallet.controllers.EnergyController
import com.energolabs.evergo.modules.houseCharts.models.EnergyUsageData
import com.energolabs.evergo.modules.houseCharts.models.GetLineGraphResponse
import com.energolabs.evergo.modules.houseCharts.models.GetPieGraphResponse
import com.energolabs.evergo.modules.houseCharts.requests.GetLineGraphRequest
import com.energolabs.evergo.modules.houseCharts.requests.GetPieGraphRequest
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.ui.views.EnergoLineChart
import com.energolabs.evergo.ui.views.EnergoLineDataSet
import com.energolabs.evergo.ui.views.YAxisRendererInsetGrid
import com.energolabs.evergo.utils.CollectionUtils
import com.energolabs.evergo.utils.DateUtils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.*

/**
 * Created by Jose Duque on 2/24/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class HouseChartFragment : BaseFragment(),
        RadioGroup.OnCheckedChangeListener,
        TabLayout.OnTabSelectedListener {

    private var tv_main_label: TextView? = null
    private var tv_main_value: TextView? = null
    private var tv_main_unit: TextView? = null
    private var lineChart: EnergoLineChart? = null

    private var iv_left_icon: ImageView? = null
    private var tv_left_label: TextView? = null
    private var tv_left_value: TextView? = null
    private var tv_left_unit: TextView? = null

    private var iv_right_icon: ImageView? = null
    private var tv_right_label: TextView? = null
    private var tv_right_value: TextView? = null
    private var tv_right_unit: TextView? = null

    private var rg_time_frames: RadioGroup? = null
    private var viewPager: ViewPager? = null
    private var tl_viewpager_indicator: TabLayout? = null
    private var tv_tab_layout_label: TextView? = null
    private var pieChart: PieChart? = null
    private var pieDataSet: PieDataSet? = null

    private var lineGraphEnergyUnit: String? = null
    private var pieGraphEnergyUnit: String? = null
    private val lineGraphData = ArrayList<EnergyUsageData>()
    private val pieGraphData = ArrayList<EnergyUsageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_house_chart

    override fun findViews(view: View) {
        tv_main_label = view.findViewById(R.id.tv_main_label) as TextView
        tv_main_value = view.findViewById(R.id.tv_main_value) as TextView
        tv_main_unit = view.findViewById(R.id.tv_main_unit) as TextView
        lineChart = view.findViewById(R.id.lineChart) as EnergoLineChart
        initLineChart()

        iv_left_icon = view.findViewById(R.id.iv_left_icon) as ImageView
        tv_left_label = view.findViewById(R.id.tv_left_label) as TextView
        tv_left_value = view.findViewById(R.id.tv_left_value) as TextView
        tv_left_unit = view.findViewById(R.id.tv_left_unit) as TextView

        iv_right_icon = view.findViewById(R.id.iv_right_icon) as ImageView
        tv_right_label = view.findViewById(R.id.tv_right_label) as TextView
        tv_right_value = view.findViewById(R.id.tv_right_value) as TextView
        tv_right_unit = view.findViewById(R.id.tv_right_unit) as TextView

        rg_time_frames = view.findViewById(R.id.rg_time_frames) as RadioGroup

        viewPager = view.findViewById(R.id.viewPager) as ViewPager
        tl_viewpager_indicator = view.findViewById(R.id.tl_viewpager_indicator) as TabLayout
        tv_tab_layout_label = view.findViewById(R.id.tv_tab_layout_label) as TextView
        viewPager?.adapter = viewPagerAdapter
        tl_viewpager_indicator?.setupWithViewPager(viewPager)

        pieChart = view.findViewById(R.id.pieChart) as PieChart
        pieChart?.isEnabled = false
        pieChart?.setTouchEnabled(false)
        initPieChart()

        requestData()
    }

    private fun initLineChart() {
        lineChart?.description?.isEnabled = false
        lineChart?.setTouchEnabled(false)
        lineChart?.dragDecelerationFrictionCoef = 0.9f
        lineChart?.isDragEnabled = false
        lineChart?.setScaleEnabled(false)
        lineChart?.setDrawGridBackground(false)
        lineChart?.isHighlightPerDragEnabled = false
        lineChart?.isHighlightPerTapEnabled = false
        lineChart?.setBackgroundColor(
                resources.getColor(android.R.color.transparent)
        )
        lineChart?.setViewPortOffsets(0f, 0f, 0f, 0f)

        val x = lineChart?.xAxis
        x?.isEnabled = false

        val axisRight = lineChart?.axisRight
        axisRight?.setDrawGridLines(false)
        axisRight?.setDrawGridLines(true)
        axisRight?.labelCount = 3
        axisRight?.setDrawZeroLine(false)
        axisRight?.setDrawLabels(true)
        axisRight?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        axisRight?.valueFormatter = IAxisValueFormatter { value, axis -> Math.round(value).toString() }
        axisRight?.textColor = Color.LTGRAY
        axisRight?.textSize = 12f
        val yAxisRendererInsetGridRight = YAxisRendererInsetGrid(
                lineChart?.viewPortHandler ?: return,
                lineChart?.axisRight ?: return,
                lineChart?.getTransformer(YAxis.AxisDependency.RIGHT) ?: return
        )
        yAxisRendererInsetGridRight.inset = 64f
        lineChart?.rendererRightYAxis = yAxisRendererInsetGridRight

        val axisLeft = lineChart?.axisLeft
        axisLeft?.setDrawGridLines(true)
        axisLeft?.labelCount = 3
        axisLeft?.setDrawZeroLine(false)
        axisLeft?.setDrawLabels(true)
        axisLeft?.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        axisLeft?.valueFormatter = IAxisValueFormatter { value, axis -> Math.round(value).toString() }
        axisLeft?.textColor = Color.LTGRAY
        axisLeft?.textSize = 12f
        val yAxisRendererInsetGrid = YAxisRendererInsetGrid(
                lineChart?.viewPortHandler ?: return,
                lineChart?.axisLeft ?: return,
                lineChart?.getTransformer(YAxis.AxisDependency.LEFT) ?: return
        )
        yAxisRendererInsetGrid.inset = 64f
        lineChart?.rendererLeftYAxis = yAxisRendererInsetGrid

        lineChart?.legend?.isEnabled = false

        lineChart?.invalidate()
    }

    private fun initPieChart() {
        //Initialize pie chart style
        pieChart?.description?.isEnabled = false
        pieChart?.isHighlightPerTapEnabled = false

        // CENTER HOLE
        pieChart?.isDrawHoleEnabled = true
        pieChart?.setHoleColor(Color.TRANSPARENT)
        pieChart?.holeRadius = 85f

        // NO LABELS FOR ENTRIES
        pieChart?.setDrawEntryLabels(false)

        // SETUP LEGEND
        pieChart?.legend?.isEnabled = false
    }

    override fun setListeners() {
        super.setListeners()
        rg_time_frames?.setOnCheckedChangeListener(this)
        tl_viewpager_indicator?.addOnTabSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_house_chart_title)
    }

    private fun requestData() {
        requestLineChartData(GetLineGraphRequest.DAILY)
        requestPieChartData(GetPieGraphRequest.WEEKLY)
    }

    private fun requestLineChartData(type: String) {
        GetLineGraphRequest
                .Builder(context ?: return)
                .setPeriod(type)
                .setResultListener(getLineChartResultListener)
                .request()
    }

    private val getLineChartResultListener = object : GetLineGraphRequest.ResultListener {

        override fun onResultSuccess(
                tag: Any?,
                response: GetLineGraphResponse?
        ) {
            lineGraphEnergyUnit = response?.energyUnit
            updateLineChartData(
                    response?.data
            )
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {
            Log.e("HouseCharts", "" + (error ?: ""))
        }

    }

    private fun requestPieChartData(type: String) {
        GetPieGraphRequest
                .Builder(context ?: return)
                .setPeriod(type)
                .setResultListener(getPieChartResultListener)
                .request()
    }

    private val getPieChartResultListener = object : GetPieGraphRequest.ResultListener {

        override fun onResultSuccess(
                tag: Any?,
                response: GetPieGraphResponse?
        ) {
            pieGraphEnergyUnit = response?.energyUnit
            updatePieChartData(
                    response?.data
            )
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {

        }

    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    private fun updateLineChart(
            color: String?,
            data: List<EnergyUsageData>?
    ) {
        if (CollectionUtils.isEmpty(data)) {
            return
        }

        val values = ArrayList<Entry>()
        var startTime: Long = 0

        data?.sortedBy {
            it.createdAt
        }?.forEach {
            var time = DateUtils.getDateFromISO8601String(it.createdAt).time
            if (startTime == 0L) {
                startTime = time
            }
            time -= startTime
            values.add(
                    Entry(
                            time.toFloat(),
                            EnergyController.getRealValue(it.amount).toFloat(),
                            it
                    )
            )
        }

        val lineDataSet = EnergoLineDataSet(values, "Energy Usage")
        lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.color = Color.parseColor(color)
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.lineWidth = 2f

        // CIRCLES
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setCircleColorHole(
                Color.YELLOW
        )
        lineDataSet.circleRadius = 6f
        lineDataSet.circleHoleRadius = 4f
        lineDataSet.setCircleColor(
                Color.parseColor(color)
        )

        lineDataSet.setDrawValues(true)
        lineDataSet.fillAlpha = 100
        lineDataSet.fillColor = Color.WHITE

        // VERTICAL/HORIZONTAL HIGHLIGHT
        lineDataSet.highLightColor = Color.WHITE
        lineDataSet.setDrawHighlightIndicators(true)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.enableDashedHighlightLine(
                9f, 9f, 0f
        )

        val lineData = LineData(lineDataSet)
        lineData.setValueTextColor(Color.WHITE)
        lineData.setValueTextSize(
                12f
        )

        lineChart?.data = lineData

        lineChart?.invalidate()

        lineChart?.highlightValue(
                values[CollectionUtils.size(values) - 1].x,
                0
        )
    }

    private fun updatePieChart(
            data: List<EnergyUsageData>
    ) {
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()
        var i = 0
        while (CollectionUtils.size(data) > i) {
            val item = data[i]
            entries.add(
                    PieEntry(
                            EnergyController.getRealValue(item.amount).toFloat()
                    )
            )
            colors.add(
                    Color.parseColor(
                            item.color
                    )
            )
            i++
        }
        pieDataSet = PieDataSet(
                entries,
                ""
        )
        pieDataSet?.colors = colors
        pieDataSet?.setValueTextColors(colors)
        pieChart?.data = PieData(pieDataSet)

        pieChart?.invalidate()
    }

    private fun updatePieChartData(data: List<EnergyUsageData>?) {
        // UPDATE LIST OF DATA
        this.pieGraphData.clear()
        if (!CollectionUtils.isEmpty(data) && data != null) {
            this.pieGraphData.addAll(data)
        }

        // UPDATE VIEW PAGER
        viewPagerAdapter.notifyDataSetChanged()

        // UPDATE PIE CHART
        updatePieChart(
                this.pieGraphData
        )
    }

    private fun updateLineChartData(data: List<EnergyUsageData>?) {
        // UPDATE LIST OF DATA
        this.lineGraphData.clear()
        if (CollectionUtils.isEmpty(data) || data == null) {
            return
        }

        this.lineGraphData.addAll(data)

        // UPDATE LINE CHART
        if (this.lineGraphData.size <= 0) {
            return
        }
        val mainData = this.lineGraphData[0]
        updateLineChart(
                mainData.color,
                mainData.graphData
        )
        tv_main_label?.text = mainData.label
        tv_main_unit?.text = "/" + (lineGraphEnergyUnit ?: "")
        tv_main_value?.text = baseActivity?.getString(
                R.string.energo_energy_format,
                EnergyController.getRealValue(mainData.amount)
        )

        // UPDATE PRODUCED DATA
        if (this.lineGraphData.size <= 1) {
            return
        }
        val producedData = this.lineGraphData[1]
        tv_left_label?.text = producedData.label
        tv_left_unit?.text = "/" + (lineGraphEnergyUnit ?: "")
        tv_left_value?.text = baseActivity?.getString(
                R.string.energo_energy_format,
                EnergyController.getRealValue(producedData.amount)
        )

        // UPDATE SAVING DATA
        if (this.lineGraphData.size <= 2) {
            return
        }
        val savingsData = this.lineGraphData[2]
        tv_right_label?.text = savingsData.label
        tv_right_unit?.text = "/" + (lineGraphEnergyUnit ?: "")
        tv_right_value?.text = baseActivity?.getString(
                R.string.energo_energy_format,
                EnergyController.getRealValue(savingsData.amount)
        )
    }

    private val viewPagerAdapter = object : PagerAdapter() {

        override fun getCount(): Int {
            return CollectionUtils.size(pieGraphData)
        }

        override fun isViewFromObject(
                view: View,
                `object`: Any
        ): Boolean {
            return view === `object`
        }

        override fun instantiateItem(
                container: ViewGroup,
                position: Int
        ): View {
            // INIT VIEW
            val itemView = inflater?.inflate(
                    R.layout.page_house_chart_proportion,
                    container,
                    false
            )

            val item = pieGraphData[position]

            val tv_label = itemView?.findViewById(R.id.tv_label) as TextView
            val tv_amount_value = itemView.findViewById(R.id.tv_amount_value) as TextView
            val tv_amount_unit = itemView.findViewById(R.id.tv_amount_unit) as TextView

            tv_label.text = item.label
            tv_amount_value.text = baseActivity?.getString(
                    R.string.energo_energy_format,
                    EnergyController.getRealValue(item.amount)
            )
            tv_amount_unit.text = pieGraphEnergyUnit

            container.addView(itemView)

            return itemView
        }

        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun destroyItem(
                container: ViewGroup,
                position: Int,
                `object`: Any
        ) {
            if (`object` !is View) {
                return
            }

            container.removeView(
                    `object`
            )
        }

    }

    private fun updateHighlight(position: Int) {
        if (pieDataSet == null) {
            return
        }

        pieChart?.highlightValue(
                position.toFloat(),
                0
        )
    }

    override fun onCheckedChanged(
            group: RadioGroup,
            checkedId: Int
    ) {
        when (checkedId) {
            R.id.rb_day_label -> {
                requestPieChartData(GetPieGraphRequest.DAILY)
            }
            R.id.rb_week_label -> {
                requestPieChartData(GetPieGraphRequest.WEEKLY)
            }
            R.id.rb_month_label -> {
                requestPieChartData(GetPieGraphRequest.MONTHLY)
            }
            R.id.rb_year_label -> {
                requestPieChartData(GetPieGraphRequest.YEARLY)
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val position = tab.position
        updateHighlight(
                position
        )

        if (pieGraphData.size < position) {
            return
        }

        tv_tab_layout_label?.text = pieGraphData[position].label
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    companion object {

        fun makeArguments(): Bundle? {
            return null
        }
    }
}

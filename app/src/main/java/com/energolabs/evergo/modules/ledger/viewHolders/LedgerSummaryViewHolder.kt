package com.energolabs.evergo.modules.ledger.viewHolders

import android.animation.LayoutTransition
import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController
import com.energolabs.evergo.modules.ledger.adapters.LedgerTransactionListAdapter
import com.energolabs.evergo.modules.ledger.models.LedgerSummaryModel
import com.energolabs.evergo.modules.ledger.models.TransactionWrapperModel
import com.energolabs.evergo.modules.ledger.viewModels.LedgerSeeAllViewModel
import com.energolabs.evergo.ui.viewHolders.BaseFlexibleViewHolder
import com.energolabs.evergo.ui.viewModels.BaseViewModel
import com.energolabs.evergo.utils.CollectionUtils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import eu.davidea.flexibleadapter.FlexibleAdapter
import java.util.*

/**
 * Created by Jose Duque on 9/11/2015.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LedgerSummaryViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder<LedgerSummaryModel>(
        view,
        activity,
        flexibleAdapter,
        false
) {

    // VIEWS
    private var rl_root: RelativeLayout? = null

    private var tv_label: TextView? = null
    private var iv_arrow: ImageView? = null

    private var tv_purchases_value: TextView? = null
    private var tv_sells_value: TextView? = null
    private var tv_others_value: TextView? = null
    private var pieChart: PieChart? = null

    private var rv_items: RecyclerView? = null
    private var adapter: LedgerTransactionListAdapter? = null

    override fun findViews(view: View) {
        super.findViews(view)
        rl_root = view.findViewById(R.id.rl_root) as RelativeLayout

        rv_items = view.findViewById(R.id.rv_items) as RecyclerView
        rv_items?.layoutManager = LinearLayoutManager(activity)

        tv_label = view.findViewById(R.id.tv_label) as TextView
        iv_arrow = view.findViewById(R.id.iv_arrow) as ImageView

        tv_purchases_value = view.findViewById(R.id.tv_purchases_value) as TextView
        tv_sells_value = view.findViewById(R.id.tv_sells_value) as TextView
        tv_others_value = view.findViewById(R.id.tv_others_value) as TextView
        pieChart = view.findViewById(R.id.pieChart) as PieChart
        initPieChart()
    }

    private fun initPieChart() {
        //Initialize pie chart style
        pieChart?.description?.isEnabled = false
        pieChart?.isHighlightPerTapEnabled = false

        // CENTER HOLE
        pieChart?.isDrawHoleEnabled = true
        pieChart?.setHoleColor(Color.WHITE)

        // CENTER TEXT
        pieChart?.setCenterTextColor(
                resources?.getColor(R.color.energo_text_color_primary) ?: 0
        )
        pieChart?.centerTextRadiusPercent = 85f
        pieChart?.setDrawCenterText(true)

        // TRANSPARENT CIRCLE

        // NO LABELS FOR ENTRIES
        pieChart?.setDrawEntryLabels(false)

        // SETUP LEGEND
        pieChart?.legend?.isEnabled = false
    }

    override fun setListeners() {
        super.setListeners()
        rootView.setOnClickListener(this)
    }

    override fun updateView() {
        super.updateView()
        updateLabel(
                item?.label
        )

        val purchases = item?.purchases
        updatePurchases(
                purchases
        )

        val sells = item?.sells
        updateSells(
                sells
        )

        val others = item?.others
        updateOthers(
                others
        )

        updatePieChart(
                CurrencyController.getRealValue(purchases ?: 0),
                CurrencyController.getRealValue(sells ?: 0),
                CurrencyController.getRealValue(others ?: 0)
        )
    }

    private fun updateLabel(month: String?) {
        tv_label?.text = month
    }

    private fun updatePurchases(transactions: Long?) {
        updateValue(
                tv_purchases_value,
                transactions
        )
    }

    private fun updateSells(transactions: Long?) {
        updateValue(
                tv_sells_value,
                transactions
        )
    }

    private fun updateOthers(others: Long?) {
        updateValue(
                tv_others_value,
                others
        )
    }

    private fun updateValue(
            textView: TextView?,
            value: Long?
    ) {
        textView?.text = resources?.getString(
                R.string.energo_currency_format,
                CurrencyController.getRealValue(value ?: 0)
        )
    }

    private fun updatePieChart(
            purchases: Double,
            sells: Double,
            others: Double
    ) {
        val entries = ArrayList<PieEntry>()
        entries.add(
                PieEntry(purchases.toFloat())
        )
        entries.add(
                PieEntry(sells.toFloat())
        )
        entries.add(
                PieEntry(others.toFloat())
        )
        val pieDataSet = PieDataSet(
                entries,
                ""
        )
        val colors = ArrayList<Int>()
        colors.add(
                resources?.getColor(R.color.energo_ledger_purchases) ?: 0
        )
        colors.add(
                resources?.getColor(R.color.energo_ledger_sells) ?: 0
        )
        colors.add(
                resources?.getColor(R.color.energo_ledger_others) ?: 0
        )
        pieDataSet.colors = colors
        pieDataSet.setValueTextColors(colors)
        pieChart?.data = PieData(pieDataSet)
        pieChart?.centerText = resources?.getString(
                R.string.energo_currency_format,
                purchases + sells + others
        )
        pieChart?.invalidate()
    }

    private fun updateTransactionsList(transactions: List<TransactionWrapperModel>?) {
        assertAdapter()
        updateViewModels(transactions)
    }

    private fun assertAdapter() {
        if (adapter != null) {
            return
        }
        adapter = LedgerTransactionListAdapter(
                ArrayList<BaseViewModel<*, *>>()
        )
        rv_items?.adapter = adapter
    }

    private fun updateViewModels(items: List<TransactionWrapperModel>?) {
        if (CollectionUtils.isEmpty(items)) {
            adapter?.updateDataSet(null)
            rv_items?.visibility = View.GONE
            return
        }

        rv_items?.visibility = View.VISIBLE
        val viewModels = ArrayList<BaseViewModel<*, *>>()
        items?.forEach {
            val viewModel = LedgerTransactionListAdapter.makeViewModel(
                    activity,
                    it
            )

            viewModels.add(
                    viewModel
            )
        }

        viewModels.add(
                LedgerSeeAllViewModel(
                        activity,
                        item
                )
        )

        adapter?.updateDataSet(viewModels)
    }

    fun updateTransactionList(
            isTransactionListVisible: Boolean,
            animated: Boolean
    ) {
        val items = item?.transactionList
        if (CollectionUtils.isEmpty(items)) {
            hideTransactions()
            iv_arrow?.setImageResource(0)
        } else if (isTransactionListVisible) {
            displayTransactions(
                    items,
                    animated
            )
            // OPEN
            iv_arrow?.setImageResource(R.drawable.ic_arrow_down)
        } else {
            hideTransactions()
            // CLOSE
            iv_arrow?.setImageResource(R.drawable.ic_arrow_right)
        }
    }

    private fun displayTransactions(
            transactionModels: List<TransactionWrapperModel>?,
            animated: Boolean
    ) {
        rl_root?.layoutTransition = if (animated) LayoutTransition() else null
        rv_items?.visibility = View.VISIBLE
        updateTransactionsList(transactionModels)
    }

    private fun hideTransactions() {
        rl_root?.layoutTransition = null
        rv_items?.visibility = View.GONE
    }

    private fun onTransactionListStatusChanged() {
        val isTransactionListVisible = !(viewModel?.expanded ?: false)
        viewModel?.expanded = isTransactionListVisible
        updateTransactionList(
                isTransactionListVisible,
                true
        )
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            else -> {
                onTransactionListStatusChanged()
            }
        }
    }

}

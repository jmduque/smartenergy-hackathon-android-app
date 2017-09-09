package com.energolabs.evergo.ui.fragments

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ViewsController
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.viewModels.LoadMoreViewModel
import com.energolabs.evergo.utils.CollectionUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import java.io.Serializable
import java.util.*

/**
 * Created by Jose on 11/2/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
abstract class BaseListFragment<
        A : FlexibleAdapter<out IFlexible<out RecyclerView.ViewHolder>>,
        out VM : AbstractFlexibleItem<out RecyclerView.ViewHolder>,
        DM : Serializable
        > : BaseFragment(),
        BaseResultListener<List<DM>> {

    // VIEWS
    protected var srl_refresh_layout: SwipeRefreshLayout? = null
    protected var rv_items: RecyclerView? = null
    protected var adapter: A? = null
    private var empty_list_view: View? = null

    override val layoutId: Int
        get() = R.layout.swipe_refresh_layout_recyclerview

    protected abstract fun makeAdapter(): A

    protected open fun makeLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(
                activity
        )
    }

    protected fun makeItemAnimator(): RecyclerView.ItemAnimator {
        return DefaultItemAnimator()
    }

    protected fun makeLoadMoreItem(): AbstractFlexibleItem<out RecyclerView.ViewHolder> {
        return LoadMoreViewModel(
                activity
        )
    }

    override fun findViews(view: View) {
        // ITEMS AREA
        srl_refresh_layout = view.findViewById(R.id.srl_refresh_layout) as SwipeRefreshLayout
        rv_items = view.findViewById(R.id.rv_items) as RecyclerView
        adapter = makeAdapter()
        rv_items?.layoutManager = makeLayoutManager()
        rv_items?.itemAnimator = makeItemAnimator()
        val decor = makeItemDecorator()
        if (decor != null) {
            rv_items?.addItemDecoration(
                    decor
            )
        }
        rv_items?.adapter = adapter
        empty_list_view = view.findViewById(emptyListViewId)

        // REQUEST INITIAL DATA
        requestData()
    }

    protected fun makeItemDecorator(): RecyclerView.ItemDecoration? {
        return null
    }

    open val emptyListViewId: Int = 0

    private fun getLocalAdapter(): FlexibleAdapter<IFlexible<out RecyclerView.ViewHolder>>? {
        return adapter as FlexibleAdapter<IFlexible<out RecyclerView.ViewHolder>>?
    }

    override fun setListeners() {
        super.setListeners()
        srl_refresh_layout?.setOnRefreshListener { requestData() }

        val localAdapter = getLocalAdapter()
        localAdapter?.setEndlessScrollListener(
                {
                    loadMoreData()
                },
                makeLoadMoreItem()
        )
    }

    protected abstract fun requestData()

    protected abstract fun loadMoreData()

    protected fun onGetItemsSuccess(items: List<DM>?) {
        cancelRefreshing()
        updateList(items)
    }

    protected fun onGetMoreItemsSuccess(items: List<DM>?) {
        cancelRefreshing()
        addToList(items)
    }

    protected fun cancelRefreshing() {
        srl_refresh_layout?.isRefreshing = false
        hideWaitDialog()
    }

    protected abstract fun makeViewModel(
            item: DM?
    ): VM

    protected open fun updateList(itemsList: List<DM>?) {
        val items = ArrayList<AbstractFlexibleItem<out RecyclerView.ViewHolder>>()
        itemsList?.forEach {
            items.add(
                    makeViewModel(
                            it
                    )
            )
        }

        ViewsController.setVisible(
                CollectionUtils.isEmpty(items),
                empty_list_view
        )

        val localAdapter = getLocalAdapter()
        localAdapter?.updateDataSet(
                items as List<IFlexible<out RecyclerView.ViewHolder>>?
        )
    }

    protected fun addToList(newItemsList: List<DM>?) {
        val newItems = ArrayList<AbstractFlexibleItem<out RecyclerView.ViewHolder>>()
        newItemsList?.forEach {
            newItems.add(
                    makeViewModel(
                            it
                    )
            )
        }

        val localAdapter = getLocalAdapter()
        localAdapter?.onLoadMoreComplete(
                newItems as List<IFlexible<out RecyclerView.ViewHolder>>?
        )
    }

    open val listedItemsCount: Int
        get() {
            return adapter?.getItemCountOfTypes(
                    makeViewModel(null)?.layoutRes
            ) ?: 0
        }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {
        when (tag) {
            GET_ITEMS -> onGetItemsSuccess(null)
            GET_MORE_ITEMS -> onGetMoreItemsSuccess(null)
        }
    }

    override fun onResultSuccess(
            tag: Any?,
            response: List<DM>?
    ) {
        when (tag) {
            GET_ITEMS -> onGetItemsSuccess(response)
            GET_MORE_ITEMS -> onGetMoreItemsSuccess(response)
        }
    }

    companion object {
        val GET_ITEMS = "getItems"
        val GET_MORE_ITEMS = "getMoreItems"

        protected val ITEMS_PER_REQUEST = 5
    }

}

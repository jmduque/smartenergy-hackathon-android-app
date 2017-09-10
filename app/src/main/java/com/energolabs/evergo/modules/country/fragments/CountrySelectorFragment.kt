package com.energolabs.evergo.modules.country.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.View
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.country.adapters.CountryModelListAdapter
import com.energolabs.evergo.modules.country.models.CountryModel
import com.energolabs.evergo.modules.country.viewModels.CountryModelViewModel
import com.energolabs.evergo.ui.fragments.BaseListFragment
import java.util.*

/**
 * Created by Jose Duque on 1/23/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class CountrySelectorFragment : BaseListFragment<
        CountryModelListAdapter,
        CountryModelViewModel,
        CountryModel>(),
        SearchView.OnQueryTextListener {

    private var sv_search: SearchView? = null

    private val countryModelList = ArrayList<CountryModel>()

    override val layoutId: Int
        get() = R.layout.fragment_country_selector

    override fun findViews(view: View) {
        super.findViews(view)
        sv_search = view.findViewById(R.id.sv_search) as SearchView
    }

    override fun makeLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(
                activity,
                3
        )
    }

    override fun setListeners() {
        super.setListeners()
        sv_search?.setOnQueryTextListener(this)
    }

    override fun makeAdapter(): CountryModelListAdapter {
        return CountryModelListAdapter(
                ArrayList<CountryModelViewModel>()
        )
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_country_selector_title)
    }

    override fun requestData() {
        val thailandModel = CountryModel()
        thailandModel.name = "Thailand"
        thailandModel.phoneCode = "+66"
        countryModelList.add(thailandModel)

        val philippinesModel = CountryModel()
        philippinesModel.name = "Philippines"
        philippinesModel.phoneCode = "+63"
        countryModelList.add(philippinesModel)

        val chinaModel = CountryModel()
        chinaModel.name = "China"
        chinaModel.phoneCode = "+86"
        countryModelList.add(chinaModel)

//        val taiwanModel = CountryModel()
//        taiwanModel.name = "Taiwan"
//        taiwanModel.phoneCode = "+886"
//        countryModelList.add(taiwanModel)

        val usModel = CountryModel()
        usModel.name = "United States"
        usModel.phoneCode = "+1"
        countryModelList.add(usModel)

        onGetItemsSuccess(
                countryModelList
        )
    }

    override fun loadMoreData() {
        onGetMoreItemsSuccess(null)
    }

    override fun makeViewModel(item: CountryModel?): CountryModelViewModel {
        return CountryModelViewModel(
                activity,
                item
        )
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onResultSuccess(
            tag: Any?,
            response: List<CountryModel>?
    ) {
        if (response != null) {
            countryModelList.clear()
            countryModelList.addAll(response)
        }
        super.onResultSuccess(
                tag,
                response
        )
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        val validCountries = ArrayList<CountryModel>()
        for (i in countryModelList.indices) {
            val countryModel = countryModelList[i]
            if (TextUtils.isEmpty(s)) {
                validCountries.add(countryModel)
                continue
            }

            if (TextUtils.indexOf(
                    countryModel.name?.toLowerCase(Locale.getDefault()),
                    s.toLowerCase(Locale.getDefault())
            ) >= 0) {
                validCountries.add(countryModel)
                continue
            }

            if (TextUtils.indexOf(countryModel.phoneCode, s) >= 0) {
                validCountries.add(countryModel)
            }
        }
        onGetItemsSuccess(
                validCountries
        )
        return false
    }

    companion object {
        fun makeArguments(): Bundle? {
            return null
        }
    }
}

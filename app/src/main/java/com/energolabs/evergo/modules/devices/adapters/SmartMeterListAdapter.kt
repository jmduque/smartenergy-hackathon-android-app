package com.energolabs.evergo.modules.devices.adapters

import com.energolabs.evergo.modules.devices.viewModels.SmartMeterViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 *
 *
 * Adapter for BuildingList.
 */
class SmartMeterListAdapter(
        items: List<SmartMeterViewModel>
) : FlexibleAdapter<SmartMeterViewModel>(items)

package com.energolabs.evergo.modules.battery.adapters

import com.energolabs.evergo.modules.battery.viewModels.BatteryViewModel
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 8/30/2016.
 *
 *
 * Adapter for BuildingList.
 */
class BatteryListAdapter(
        items: List<BatteryViewModel>
) : FlexibleAdapter<BatteryViewModel>(items)
package com.energolabs.evergo.modules.houseCharts.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 2/24/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class EnergyUsageData : BaseModel() {

    var label: String? = null
    var amount: Long = 0
    var color: String? = null
    var graphData: List<EnergyUsageData>? = null

}

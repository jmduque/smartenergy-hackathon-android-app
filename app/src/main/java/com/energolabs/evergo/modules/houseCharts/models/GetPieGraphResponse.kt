package com.energolabs.evergo.modules.houseCharts.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 2/24/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class GetPieGraphResponse : BaseModel() {

    var energyUnit: String? = null
    var data: List<EnergyUsageData>? = null

}

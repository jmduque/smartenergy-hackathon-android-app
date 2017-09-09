package com.energolabs.evergo.modules.battery.models

import com.energolabs.evergo.models.BaseModel
import com.energolabs.evergo.modules.location.models.GeoLocationModel

/**
 * Created by Jose Duque on 9/9/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class BatteryModel : BaseModel() {

    var name: String? = null
    var location: GeoLocationModel? = null
    var type: String? = null
    var status: String? = null
    var capacity: Double? = null
    var charge: Double? = null

}

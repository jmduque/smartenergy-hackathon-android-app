package com.energolabs.evergo.modules.devices.models

import com.energolabs.evergo.models.BaseModel
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.profile.models.UserModel

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class DeviceInfo : BaseModel() {

    var name: String? = null
    var owner: UserModel? = null
    var location: LocationModel? = null
    var address: String? = null
    var deviceStatus: String? = null
    var gridStatus: String? = null
    var accessTime: String? = null
    var model: String? = null
    var hwVersion: String? = null
    var uuid: String? = null
    var type: String? = null

}

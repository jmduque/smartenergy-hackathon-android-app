package com.energolabs.evergo.modules.user.profile.models

import com.energolabs.evergo.models.BaseModel
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.validation.models.IdentityModel

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class UserModel : BaseModel() {

    var phoneNumber: String? = null
    var countryCode: String? = null
    var identity: IdentityModel? = null
    var location: LocationModel? = null
    var name: String? = null
    var address: String? = null
    var gridName: String? = null
    var gender: Int = 0
    var role: String? = null
    var password: String? = null
    var walletId: String? = null
    var avatar: String? = null
    var hasPaymentPassword: Boolean = false

}

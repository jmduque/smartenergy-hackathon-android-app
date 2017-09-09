package com.energolabs.evergo.modules.auth.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 1/24/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class PhoneCodeRequest : BaseModel() {

    var phoneNumber: String? = null
    var type: String? = null

}

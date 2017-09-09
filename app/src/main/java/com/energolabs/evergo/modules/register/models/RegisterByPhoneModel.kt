package com.energolabs.evergo.modules.register.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class RegisterByPhoneModel : BaseModel() {

    var phoneNumber: String? = null
    var phoneCode: String? = null
    /**
     * @param password MD5 Encrypted Password
     */
    var password: String? = null
}

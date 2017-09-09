package com.energolabs.evergo.modules.password.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class ForgotPasswordByPhoneModel : BaseModel() {

    var phoneNumber: String? = null
    var phoneCode: String? = null
    /**
     * @param newPassword MD5 Encrypted Password
     */
    var newPassword: String? = null

}

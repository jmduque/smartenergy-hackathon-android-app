package com.energolabs.evergo.modules.auth.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by jose_duque on 12/2/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class AuthModel : BaseModel() {

    var userId: String? = null
    var accessToken: String? = null
    var refreshToken: String? = null
    var role: String? = null

}

package com.energolabs.evergo.modules.settings.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
open class SettingsItem : BaseModel() {

    var type: String? = null
    var name: String? = null
    var icon: String? = null

}

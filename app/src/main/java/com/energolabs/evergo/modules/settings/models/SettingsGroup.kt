package com.energolabs.evergo.modules.settings.models

import com.energolabs.evergo.models.BaseModel

import java.util.ArrayList

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsGroup : BaseModel() {

    var type: String? = null
    var items: ArrayList<SettingsItem>? = null
}

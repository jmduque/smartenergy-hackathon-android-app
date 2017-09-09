package com.energolabs.evergo.modules.main.models

import com.energolabs.evergo.models.BaseModel

/**
 * Created by Jose Duque on 12/13/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class MainModel : BaseModel() {

    var energyBalance: Long = 0
    var energyCreated: Long = 0
    var energyUsed: Long = 0
    var marketPrice: Long = 0

}

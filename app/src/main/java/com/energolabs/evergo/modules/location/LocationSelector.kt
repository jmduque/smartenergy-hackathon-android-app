package com.energolabs.evergo.modules.location

/**
 * Created by Jose Duque on 2/9/17.
 * Copyright (C) 2017 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

interface LocationSelector {
    companion object {
        val SELECT_LOCATION = 1000
        val SELECT_LOCATION_RESULT = 2000
        val SELECT_LOCATION_CANCEL = 3000

        val LOCATION = "location"
    }
}

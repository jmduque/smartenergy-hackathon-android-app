package com.energolabs.evergo.modules.location.adapters

import com.energolabs.evergo.modules.location.viewModels.LocationModelViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class LocationModelListAdapter(
        items: List<LocationModelViewModel>
) : FlexibleAdapter<LocationModelViewModel>(items)

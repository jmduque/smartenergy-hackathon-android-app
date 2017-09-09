package com.energolabs.evergo.modules.country.adapters

import com.energolabs.evergo.modules.country.viewModels.CountryModelViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class CountryModelListAdapter(
        items: List<CountryModelViewModel>
) : FlexibleAdapter<CountryModelViewModel>(
        items
)

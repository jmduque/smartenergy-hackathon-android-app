package com.energolabs.evergo.modules.settings.adapters

import com.energolabs.evergo.ui.viewModels.BaseViewModel

import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsItemListAdapter(
        items: List<BaseViewModel<*, *>>
) : FlexibleAdapter<BaseViewModel<*, *>>(items)

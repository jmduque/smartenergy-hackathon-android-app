package com.energolabs.evergo.modules.settings.viewHolders

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.modules.settings.models.SettingsProfileItem
import com.energolabs.evergo.modules.user.profile.fragments.UserProfileFragment
import com.energolabs.evergo.modules.user.validation.fragments.UserValidationOnGoingFragment
import com.energolabs.evergo.modules.user.validation.fragments.UserValidationTypeFragment
import com.energolabs.evergo.modules.user.validation.models.IdentityModel
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.utils.CollectionUtils
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by Jose on 11/27/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
class SettingsProfileItemViewHolder(
        view: View,
        activity: Activity?,
        flexibleAdapter: FlexibleAdapter<*>
) : BaseSettingsViewHolder<SettingsProfileItem>(
        view,
        activity,
        flexibleAdapter
) {

    private var tv_verification: TextView? = null

    override fun findViews(view: View) {
        super.findViews(view)
        tv_verification = view.findViewById(R.id.tv_verification) as TextView
    }

    override fun updateView() {
        super.updateView()
        val item = item
        val userModel = item?.userModel

        updateVerificationStatus(
                userModel?.identity
        )
    }

    private fun updateVerificationStatus(
            identityModel: IdentityModel?
    ) {
        if (identityModel?.verified ?: false) {
            tv_verification?.setText(R.string.energo_settings_profile_verified)
            tv_verification?.setBackgroundResource(R.drawable.bg_verified)
        } else {
            tv_verification?.setText(R.string.energo_settings_profile_not_verified)
            tv_verification?.setBackgroundResource(R.drawable.bg_not_verified)
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)

        val id = item?._id
        if (TextUtils.isEmpty(id)) {
            return
        }

        val userModel = item?.userModel
        val identityModel = userModel?.identity
        if (identityModel == null) {
            DetailActivityNoCollapsing.openWithFragment(
                    activity ?: return,
                    UserValidationTypeFragment::class.java.name,
                    UserValidationTypeFragment.makeArguments(userModel ?: return),
                    true
            )
            return
        }
        if (CollectionUtils.isEmpty(identityModel.photos)) {
            DetailActivityNoCollapsing.openWithFragment(
                    activity ?: return,
                    UserValidationTypeFragment::class.java.name,
                    UserValidationTypeFragment.makeArguments(userModel),
                    true
            )
            return
        }

        if (!identityModel.verified) {
            DetailActivityNoCollapsing.openWithFragment(
                    activity ?: return,
                    UserValidationOnGoingFragment::class.java.name,
                    UserValidationOnGoingFragment.makeArguments(),
                    true
            )
            return
        }

        when (id) {
            else -> {
                DetailActivityNoCollapsing.openWithFragment(
                        activity ?: return,
                        UserProfileFragment::class.java.name,
                        UserProfileFragment.makeArguments(),
                        true
                )
            }
        }
    }

}

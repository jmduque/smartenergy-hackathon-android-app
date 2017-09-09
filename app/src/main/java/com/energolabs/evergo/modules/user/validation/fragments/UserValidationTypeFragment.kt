package com.energolabs.evergo.modules.user.validation.fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup

import com.energolabs.evergo.R
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.validation.models.IdentityModel
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class UserValidationTypeFragment : BaseFragment(), View.OnClickListener {

    private var rg_type: RadioGroup? = null
    private var btn_next: View? = null

    private var userModel: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        if (args != null) {
            userModel = args.getSerializable(USER) as UserModel
        }
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_validation_type

    override fun findViews(view: View) {
        rg_type = view.findViewById(R.id.rg_type) as RadioGroup
        btn_next = view.findViewById(R.id.btn_next)
    }

    override fun setListeners() {
        super.setListeners()
        btn_next?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_user_validation_title)
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                onNextClicked()
            }
        }
    }

    private fun onNextClicked() {
        val identityModel = IdentityModel()
        when (rg_type?.checkedRadioButtonId) {
            R.id.rb_family -> {
                identityModel.accountType = "family"
            }
            R.id.rb_business -> {
                identityModel.accountType = "business"
            }
            R.id.rb_industry -> {
                identityModel.accountType = "industry"
            }
        }

        userModel?.identity = identityModel

        DetailActivityNoCollapsing.openWithFragment(
                context,
                UserValidationNameFragment::class.java.name,
                UserValidationNameFragment.makeArguments(
                        userModel ?: return
                ),
                true
        )

        activity?.finish()
    }

    companion object {

        private val USER = "USER"

        fun makeArguments(
                userModel: UserModel
        ): Bundle {
            val args = Bundle()
            args.putSerializable(
                    USER,
                    userModel
            )
            return args
        }
    }

}

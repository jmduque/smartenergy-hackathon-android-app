package com.energolabs.evergo.modules.user.profile.fragments

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.FragmentPermissionsController
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.requests.GetUserRequest
import com.energolabs.evergo.modules.user.profile.requests.PutUserRequest
import com.energolabs.evergo.modules.user.profile.requests.callbacks.AvatarUploaderListener
import com.energolabs.evergo.modules.user.profile.requests.controllers.AvatarUploaderFragmentController
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.modules.user.profile.utils.GenderUtils
import com.energolabs.evergo.modules.user.validation.models.IdentityModel
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.dialogs.TextInputDialog
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.GlideUtils

/**
 * Created by Jose Duque on 12/23/16.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class UserProfileFragment : BaseFragment(),
        View.OnClickListener,
        BaseResultListener<UserModel>,
        AvatarUploaderListener {

    private var ll_avatar: View? = null
    private var iv_avatar: ImageView? = null

    private var ll_name: View? = null
    private var tv_name: TextView? = null

    private var ll_gender: View? = null
    private var tv_gender: TextView? = null

    private var ll_type: View? = null
    private var tv_type: TextView? = null

    private var ll_address: View? = null
    private var tv_address: TextView? = null

    private var userProfilePreferences: UserProfilePreferences? = null
    private var authPreferences: AuthPreferences? = null

    private var avatarUploaderFragmentController: AvatarUploaderFragmentController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authPreferences = AuthPreferences(context)
        userProfilePreferences = UserProfilePreferences(
                context,
                authPreferences?.userId
        )
        avatarUploaderFragmentController = AvatarUploaderFragmentController(
                this,
                this
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_profile

    override fun findViews(view: View) {
        ll_avatar = view.findViewById(R.id.ll_avatar)
        iv_avatar = view.findViewById(R.id.iv_avatar) as ImageView

        ll_name = view.findViewById(R.id.ll_name)
        tv_name = view.findViewById(R.id.tv_name) as TextView

        ll_gender = view.findViewById(R.id.ll_gender)
        tv_gender = view.findViewById(R.id.tv_gender) as TextView

        ll_type = view.findViewById(R.id.ll_type)
        tv_type = view.findViewById(R.id.tv_type) as TextView

        ll_address = view.findViewById(R.id.ll_address)
        tv_address = view.findViewById(R.id.tv_address) as TextView
    }

    override fun setListeners() {
        super.setListeners()
        ll_avatar?.setOnClickListener(this)
        ll_name?.setOnClickListener(this)
        ll_gender?.setOnClickListener(this)
        ll_type?.setOnClickListener(this)
        ll_address?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.energo_user_profile_title)
        updateUser(
                userProfilePreferences?.userModel
        )
        requestUser()
    }

    private fun updateUser(
            userModel: UserModel?
    ) {
        val gender = userModel?.gender
        GlideUtils.loadImage(
                this,
                iv_avatar,
                userModel?.avatar,
                GenderUtils.getDefaultAvatarResource(gender)
        )
        tv_name?.text = userModel?.name
        updateGender(
                gender
        )
        updateType(
                userModel?.identity
        )
        updateAddress(
                userModel?.location
        )
    }

    private fun updateGender(gender: Int?) {
        tv_gender?.text = GenderUtils.getGenderText(
                context,
                gender
        )
    }

    private fun updateType(identityModel: IdentityModel?) {
        val accountType = identityModel?.accountType
        when (accountType) {
            "family" -> tv_address?.setText(R.string.energo_user_profile_type_family)
            "business" -> tv_address?.setText(R.string.energo_user_profile_type_business)
            "industry" -> tv_address?.setText(R.string.energo_user_profile_type_industry)
            else -> tv_address?.setText(R.string.energo_user_profile_type_unknown)
        }
    }

    private fun updateAddress(locationModel: LocationModel?) {
        tv_address?.text = locationModel?.areaName
                ?: getStringSafely(R.string.energo_user_profile_address_unknown)
    }

    private fun requestUser() {
        GetUserRequest
                .Builder(context ?: return)
                .setUserId(
                        authPreferences?.userId
                )
                .setResultListener(this)
                .request()
    }

    override fun disableViews() {

    }

    override fun enableViews() {

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_avatar -> {
                pickAvatar()
            }
            R.id.ll_name -> {
                inputNewName(
                        userProfilePreferences?.name
                )
            }
            R.id.ll_gender -> {
                openGenderSelector(
                        userProfilePreferences?.gender ?: GenderUtils.UNKNOWN
                )
            }
        }
    }

    override fun onResultSuccess(
            tag: Any?,
            response: UserModel?
    ) {
        userProfilePreferences?.saveUserModel(response)
        updateUser(response)
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        avatarUploaderFragmentController?.onActivityResult(
                requestCode,
                resultCode,
                data
        )
    }

    override fun onAvatarUploadedSuccess(url: String) {
        userProfilePreferences?.saveAvatar(url)
        updateUser(
                userProfilePreferences?.userModel
        )
        PutUserRequest
                .Builder(context ?: return)
                .setId(authPreferences?.userId)
                .setAvatar(url)
                .request()
    }

    override fun onAvatarUploadError() {

    }

    private fun inputNewName(currentName: String?) {
        if (true) {
            // CURRENTLY WE DON'T SUPPORT USERS TO CHANGE NAME AFTER IDENTITY VERIFICATION
            return
        }

        val textInputDialog = TextInputDialog()
        textInputDialog.showDialog(
                activity,
                (view as ViewGroup),
                R.string.energo_user_profile_name_label,
                currentName ?: "",
                R.string.energo_user_profile_name_label,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    val inputText = textInputDialog.getInput()
                    PutUserRequest.Builder(context)
                            .setId(authPreferences?.userId)
                            .setName(inputText)
                            .setResultListener(this@UserProfileFragment)
                            .request()
                }
        )
    }

    private fun indexOfGender(gender: Int): Int {
        when (gender) {
            GenderUtils.UNKNOWN -> {
                return -1
            }
            GenderUtils.MALE -> {
                return 0
            }
            GenderUtils.FEMALE -> {
                return 1
            }
            GenderUtils.OTHER -> {
                return 2
            }
            else -> {
                return -1
            }
        }
    }

    private fun genderOfIndex(index: Int): Int {
        when (index) {
            0 -> {
                return GenderUtils.MALE
            }
            1 -> {
                return GenderUtils.FEMALE
            }
            2 -> {
                return GenderUtils.OTHER
            }
            else -> {
                return GenderUtils.UNKNOWN
            }
        }
    }

    private fun openGenderSelector(gender: Int) {
        MaterialDialog
                .Builder(context ?: return)
                .title(R.string.energo_user_profile_gender_label)
                .items(
                        getStringSafely(R.string.energo_user_profile_gender_male),
                        getStringSafely(R.string.energo_user_profile_gender_female),
                        getStringSafely(R.string.energo_user_profile_gender_other)
                )
                .itemsCallbackSingleChoice(
                        indexOfGender(gender)
                ) { dialog, itemView, which, text ->
                    val newGender = genderOfIndex(which)
                    PutUserRequest
                            .Builder(context)
                            .setId(authPreferences?.userId)
                            .setGender(newGender)
                            .setResultListener(this@UserProfileFragment)
                            .request()
                    true
                }
                .show()
    }

    private fun pickAvatar() {
        val permissionsController = FragmentPermissionsController(
                this,
                activity ?: return,
                baseActivity?.main_layout ?: return
        )
        if (!permissionsController.mayUseCamera(
                REQUEST_CAMERA
        )) {
            return
        }
        if (!permissionsController.mayWriteExternalStorage(
                REQUEST_WRITE_EXTERNAL_STORAGE
        )) {
            return
        }
        if (!permissionsController.mayReadExternalStorage(
                REQUEST_READ_EXTERNAL_STORAGE
        )) {
            return
        }
        avatarUploaderFragmentController?.pickAvatar()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickAvatar()
                }
            }
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickAvatar()
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickAvatar()
                }
            }
        }
    }

    companion object {

        private val REQUEST_CAMERA = 100
        private val REQUEST_READ_EXTERNAL_STORAGE = 200
        private val REQUEST_WRITE_EXTERNAL_STORAGE = 300

        fun makeArguments(): Bundle? {
            return null
        }
    }

}
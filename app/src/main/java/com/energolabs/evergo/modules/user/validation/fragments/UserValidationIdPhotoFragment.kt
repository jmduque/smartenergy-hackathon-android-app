package com.energolabs.evergo.modules.user.validation.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.FragmentPermissionsController
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.requests.PutUserRequest
import com.energolabs.evergo.modules.user.validation.models.IdentityPhoto
import com.energolabs.evergo.modules.user.validation.requests.IdPhotoUploaderController
import com.energolabs.evergo.modules.user.validation.requests.IdPhotoUploaderFragmentController
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.fragments.BaseFragment
import com.energolabs.evergo.utils.GlideUtils
import com.energolabs.evergo.utils.ToastUtil
import java.util.*

/**
 * Created by Jose Duque on 2/6/17.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */

class UserValidationIdPhotoFragment : BaseFragment(),
        View.OnClickListener,
        BaseResultListener<UserModel> {

    private var fl_front: View? = null
    private var iv_front: ImageView? = null
    private var fl_back: View? = null
    private var iv_back: ImageView? = null
    private var lastClickedView: View? = null

    private var btn_next: View? = null

    private var userModel: UserModel? = null

    private var frontIdPhotoUploaderFragmentController: IdPhotoUploaderFragmentController? = null
    private var frontPhotoUrl: String? = null
    private var backIdPhotoUploaderFragmentController: IdPhotoUploaderFragmentController? = null
    private var backPhotoUrl: String? = null
    private var currentUploader: IdPhotoUploaderFragmentController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        if (args != null) {
            userModel = args.getSerializable(USER) as UserModel
        }
        super.onCreate(savedInstanceState)
        frontIdPhotoUploaderFragmentController = IdPhotoUploaderFragmentController(
                this,
                frontIdPhotoUploaderListener
        )
        backIdPhotoUploaderFragmentController = IdPhotoUploaderFragmentController(
                this,
                backIdPhotoUploaderListener
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_validation_id_photo

    override fun findViews(view: View) {
        fl_front = view.findViewById(R.id.fl_front)
        iv_front = view.findViewById(R.id.iv_front) as ImageView
        fl_back = view.findViewById(R.id.fl_back)
        iv_back = view.findViewById(R.id.iv_back) as ImageView

        btn_next = view.findViewById(R.id.btn_next)
    }

    override fun setListeners() {
        super.setListeners()
        fl_front?.setOnClickListener(this)
        fl_back?.setOnClickListener(this)

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

    override fun onClick(v: View?) {
        lastClickedView = v
        when (v?.id) {
            R.id.fl_front -> {
                onFrontCardClicked()
            }
            R.id.fl_back -> {
                onBackCardClicked()
            }
            R.id.btn_next -> {
                onNextClicked()
            }
        }
    }

    private fun onFrontCardClicked() {
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
        frontIdPhotoUploaderFragmentController?.pickPhoto()
        currentUploader = frontIdPhotoUploaderFragmentController
    }

    private fun onBackCardClicked() {
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
        backIdPhotoUploaderFragmentController?.pickPhoto()
        currentUploader = backIdPhotoUploaderFragmentController
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClick(lastClickedView)
                }
            }
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClick(lastClickedView)
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClick(lastClickedView)
                }
            }
        }
    }

    private fun onNextClicked() {
        if (TextUtils.isEmpty(frontPhotoUrl)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_user_validation_id_photo_front_missing_error
            )
            return
        }

        if (TextUtils.isEmpty(backPhotoUrl)) {
            ToastUtil.showToastLong(
                    activity,
                    R.string.energo_user_validation_id_photo_back_missing_error
            )
            return
        }

        val identityModel = userModel?.identity

        val photos = ArrayList<IdentityPhoto>()

        val idFrontPhoto = IdentityPhoto()
        idFrontPhoto.url = frontPhotoUrl
        idFrontPhoto.type = "id_photo_front"
        photos.add(idFrontPhoto)

        val idBackPhoto = IdentityPhoto()
        idBackPhoto.url = backPhotoUrl
        idBackPhoto.type = "id_photo_back"
        photos.add(idBackPhoto)

        identityModel?.photos = photos

        PutUserRequest
                .Builder(context ?: return)
                .setRequest(userModel)
                .setId(AuthPreferences(context ?: return).userId)
                .setResultListener(this)
                .request()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        currentUploader?.onActivityResult(
                requestCode,
                resultCode,
                data
        )
    }

    private val frontIdPhotoUploaderListener = object : IdPhotoUploaderController.IdCardUploaderListener {
        override fun onIdPhotoUploadedSuccess(url: String) {
            frontPhotoUrl = url
            GlideUtils.loadImage(
                    this@UserValidationIdPhotoFragment,
                    iv_front,
                    url,
                    0
            )
        }

        override fun onIdPhotoUploadError() {

        }
    }

    private val backIdPhotoUploaderListener = object : IdPhotoUploaderController.IdCardUploaderListener {
        override fun onIdPhotoUploadedSuccess(url: String) {
            backPhotoUrl = url
            GlideUtils.loadImage(
                    this@UserValidationIdPhotoFragment,
                    iv_back,
                    url,
                    0
            )
        }

        override fun onIdPhotoUploadError() {

        }
    }

    override fun onResultSuccess(
            tag: Any?,
            response: UserModel?
    ) {
        DetailActivityNoCollapsing.openWithFragment(
                context,
                UserValidationSuccessFragment::class.java.name,
                UserValidationSuccessFragment.makeArguments(),
                true
        )
        activity?.finish()
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {

    }

    companion object {

        private val REQUEST_CAMERA = 100
        private val REQUEST_READ_EXTERNAL_STORAGE = 200
        private val REQUEST_WRITE_EXTERNAL_STORAGE = 300

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

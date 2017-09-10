package com.energolabs.evergo.modules.main.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ActivityPermissionsController
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.battery.fragments.BatteryListFragment
import com.energolabs.evergo.modules.chargers.fragments.ChargerQRScannerFragment
import com.energolabs.evergo.modules.chargers.fragments.ChargerStartFragment
import com.energolabs.evergo.modules.chargers.fragments.ChargerStopFragment
import com.energolabs.evergo.modules.chargers.storage.ChargerPreferences
import com.energolabs.evergo.modules.currencyWallet.fragments.WalletFragment
import com.energolabs.evergo.modules.settings.fragments.SettingsFragment
import com.energolabs.evergo.modules.transactions.fragments.TransactionListFragment
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.requests.GetUserRequest
import com.energolabs.evergo.modules.user.profile.requests.PutUserRequest
import com.energolabs.evergo.modules.user.profile.requests.callbacks.AvatarUploaderListener
import com.energolabs.evergo.modules.user.profile.requests.controllers.AvatarUploaderActivityController
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.modules.user.profile.utils.GenderUtils
import com.energolabs.evergo.ui.activities.BaseActivity
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.ui.activities.DetailActivityNoToolbar
import com.energolabs.evergo.utils.GlideUtils

class MainActivity : BaseActivity(),
        View.OnClickListener,
        AvatarUploaderListener,
        PutUserRequest.PutUserRequestListener {

    private val REQUEST_CAMERA = 100
    private val REQUEST_READ_EXTERNAL_STORAGE = 200
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 300

    private var drawer_layout: DrawerLayout? = null
    private var iv_menu: View? = null

    private var tv_settings: View? = null
    private var tv_user_name: TextView? = null
    private var iv_avatar: ImageView? = null

    // SECTIONS ACCESSES
    private var tv_map: View? = null
    private var tv_wallet: View? = null
    private var tv_transactions: View? = null
    private var tv_storage: View? = null

    // MAIN CONTENT ITEMS
    private var iv_scanner: View? = null
    private var iv_my_location: View? = null
    private var iv_zoom_out: View? = null
    private var iv_zoom_in: View? = null

    // MAP
    private var iv_map: View? = null
    private var iv_marker: ImageView? = null

    // CHARGER
    private var fl_charger_info: View? = null
    private var rl_charger_info: View? = null
    private var tv_charger_action: TextView? = null
    private var tv_charger_status: TextView? = null

    private var avatarUploaderActivityController: AvatarUploaderActivityController? = null

    private var authPreferences: AuthPreferences? = null
    private var userProfilePreferences: UserProfilePreferences? = null
    private var chargerPreferences: ChargerPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        authPreferences = AuthPreferences(this)
        userProfilePreferences = UserProfilePreferences(this, authPreferences?.userId)
        chargerPreferences = ChargerPreferences(this, authPreferences?.userId)
        avatarUploaderActivityController = AvatarUploaderActivityController(
                this,
                this
        )
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.activity_new_main

    override fun findViews() {
        super.findViews()
        drawer_layout = findViewById(R.id.drawer_layout) as DrawerLayout
        iv_menu = findViewById(R.id.iv_menu)

        iv_avatar = findViewById(R.id.iv_avatar) as ImageView
        tv_user_name = findViewById(R.id.tv_user_name) as TextView
        tv_settings = findViewById(R.id.tv_settings)

        iv_scanner = findViewById(R.id.iv_scanner)
        iv_my_location = findViewById(R.id.iv_my_location)
        iv_zoom_out = findViewById(R.id.iv_zoom_out)
        iv_zoom_in = findViewById(R.id.iv_zoom_in)

        iv_map = findViewById(R.id.iv_map)
        iv_marker = findViewById(R.id.iv_marker) as ImageView

        fl_charger_info = findViewById(R.id.fl_charger_info)
        rl_charger_info = findViewById(R.id.rl_charger_info)
        tv_charger_action = findViewById(R.id.tv_charger_action) as TextView
        tv_charger_status = findViewById(R.id.tv_charger_status) as TextView

        tv_map = findViewById(R.id.tv_map)
        tv_wallet = findViewById(R.id.tv_wallet)
        tv_transactions = findViewById(R.id.tv_transactions)
        tv_storage = findViewById(R.id.tv_storage)
    }

    private fun requestUserInfo() {
        GetUserRequest.Builder(this)
                .setUserId(authPreferences?.userId)
                .setResultListener(getUserResultListener)
                .request()
    }

    override fun setupListeners() {
        super.setupListeners()
        iv_menu?.setOnClickListener(this)

        iv_avatar?.setOnClickListener(this)
        tv_settings?.setOnClickListener(this)

        tv_map?.setOnClickListener(this)
        tv_wallet?.setOnClickListener(this)
        tv_transactions?.setOnClickListener(this)
        tv_storage?.setOnClickListener(this)

        iv_map?.setOnClickListener(this)
        iv_marker?.setOnClickListener(this)
        rl_charger_info?.setOnClickListener(this)

        iv_scanner?.setOnClickListener(this)
        iv_my_location?.setOnClickListener(this)
        iv_zoom_out?.setOnClickListener(this)
        iv_zoom_in?.setOnClickListener(this)
    }

    public override fun onResume() {
        super.onResume()
        updateUserModel(
                userProfilePreferences?.userModel
        )
        requestUserInfo()

        when (chargerPreferences?.status) {
            "charging" -> showMarkerData()
            else -> hideMarkerData()
        }
    }

    public override fun onPause() {
        super.onPause()
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

    private fun updateUserModel(
            userModel: UserModel?
    ) {
        GlideUtils.loadImage(
                this@MainActivity,
                iv_avatar,
                userModel?.avatar,
                GenderUtils.getDefaultAvatarResource(userModel?.gender)
        )
        tv_user_name?.text = userModel?.name ?: getString(R.string.energo_undefined_name)
    }

    private val getUserResultListener = object : GetUserRequest.ResultListener {
        override fun onResultSuccess(
                tag: Any?,
                response: UserModel?
        ) {
            userProfilePreferences?.saveUserModel(response)
            updateUserModel(response)
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {

        }
    }

    private fun openSettings() {
        DetailActivityNoCollapsing.openWithFragment(
                this,
                SettingsFragment::class.java.name,
                SettingsFragment.makeArguments(),
                true
        )
    }

    private fun openChargerQRScanner() {
        DetailActivityNoToolbar.openWithFragment(
                this,
                ChargerQRScannerFragment::class.java.name,
                ChargerQRScannerFragment.makeArguments(),
                true
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_menu -> {
                drawer_layout?.openDrawer(Gravity.START)
            }
            R.id.iv_avatar -> {
                pickAvatar()
                drawer_layout?.closeDrawers()
            }
            R.id.tv_settings -> {
                openSettings()
                drawer_layout?.closeDrawers()
            }
            R.id.tv_map -> {
                drawer_layout?.closeDrawers()
            }
            R.id.tv_transactions -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        TransactionListFragment::class.java.name,
                        TransactionListFragment.makeArguments(),
                        true
                )
                drawer_layout?.closeDrawers()
            }
            R.id.tv_wallet -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        WalletFragment::class.java.name,
                        WalletFragment.makeArguments(),
                        true
                )
                drawer_layout?.closeDrawers()
            }
            R.id.tv_storage -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        BatteryListFragment::class.java.name,
                        BatteryListFragment.makeArguments(),
                        true
                )
                drawer_layout?.closeDrawers()
            }
            R.id.iv_scanner -> {
                openChargerQRScanner()
            }
            R.id.iv_marker -> {
                showMarkerData()
            }
            R.id.iv_map -> {
                if (chargerPreferences?.status == "charging") {
                    return
                }
                hideMarkerData()
            }
            R.id.rl_charger_info -> {
                when (chargerPreferences?.status) {
                    "available" -> {
                        DetailActivityNoCollapsing.openWithFragment(
                                this,
                                ChargerStartFragment::class.java.name,
                                ChargerStartFragment.makeArguments(),
                                true
                        )
                    }
                    "charging" -> {
                        DetailActivityNoCollapsing.openWithFragment(
                                this,
                                ChargerStopFragment::class.java.name,
                                ChargerStopFragment.makeArguments(),
                                true
                        )
                    }
                }
            }
        }
    }

    private fun showMarkerData() {
        iv_zoom_in?.visibility = View.GONE
        iv_zoom_out?.visibility = View.GONE
        iv_scanner?.visibility = View.GONE
        iv_my_location?.visibility = View.GONE

        fl_charger_info?.visibility = View.VISIBLE

        when (chargerPreferences?.status) {
            "available" -> {
                tv_charger_action?.text = getString(R.string.energo_charger_action_book)
                tv_charger_status?.text = getString(R.string.energo_charger_status_available)
                iv_marker?.setImageResource(R.drawable.ic_open_selected)
            }
            "booked" -> {
                tv_charger_action?.text = ""
                tv_charger_status?.text = getString(R.string.energo_charger_status_booked)
                iv_marker?.setImageResource(R.drawable.ic_booked)
            }
            "charging" -> {
                tv_charger_action?.text = getString(R.string.energo_charger_action_charge)
                tv_charger_status?.text = getString(R.string.energo_charger_status_charging)
                iv_marker?.setImageResource(R.drawable.ic_charging)
            }
        }
    }

    private fun hideMarkerData() {
        iv_zoom_in?.visibility = View.VISIBLE
        iv_zoom_out?.visibility = View.VISIBLE
        iv_scanner?.visibility = View.VISIBLE
        iv_my_location?.visibility = View.VISIBLE

        fl_charger_info?.visibility = View.GONE

        iv_marker?.setImageResource(R.drawable.ic_open)
    }

    private fun pickAvatar() {
        val permissionsController = ActivityPermissionsController(
                this,
                main_layout ?: return
        )
        if (!permissionsController.mayUseCamera(REQUEST_CAMERA)) {
            return
        }
        if (!permissionsController.mayWriteExternalStorage(REQUEST_WRITE_EXTERNAL_STORAGE)) {
            return
        }
        if (!permissionsController.mayReadExternalStorage(REQUEST_READ_EXTERNAL_STORAGE)) {
            return
        }
        avatarUploaderActivityController?.pickAvatar()
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        avatarUploaderActivityController?.onActivityResult(
                requestCode,
                resultCode,
                data
        )
    }

    override fun onAvatarUploadedSuccess(url: String) {
        PutUserRequest.Builder(this)
                .setId(authPreferences?.userId)
                .setAvatar(url)
                .setResultListener(this)
                .request()
    }

    override fun onAvatarUploadError() {

    }

    override fun onResultSuccess(
            tag: Any?,
            response: UserModel?
    ) {
        requestUserInfo()
    }

    override fun onResultError(
            tag: Any?,
            error: String?,
            errorCode: Int
    ) {

    }

    companion object {
        fun makeIntent(context: Context): Intent {
            return Intent(
                    context,
                    MainActivity::class.java
            )
        }
    }
}

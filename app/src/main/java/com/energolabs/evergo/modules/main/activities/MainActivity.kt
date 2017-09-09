package com.energolabs.evergo.modules.main.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dinuscxj.progressbar.CircleProgressBar
import com.energolabs.evergo.R
import com.energolabs.evergo.controllers.ActivityPermissionsController
import com.energolabs.evergo.modules.auth.storage.AuthPreferences
import com.energolabs.evergo.modules.battery.fragments.BatteryListFragment
import com.energolabs.evergo.modules.currencyWallet.fragments.WalletFragment
import com.energolabs.evergo.modules.energyWallet.controllers.EnergyController
import com.energolabs.evergo.modules.energyWallet.models.EnergyWalletModel
import com.energolabs.evergo.modules.energyWallet.requests.PostEnergyBalanceRequest
import com.energolabs.evergo.modules.energyWallet.requests.controllers.GetEnergyBalanceController
import com.energolabs.evergo.modules.houseCharts.fragments.HouseChartFragment
import com.energolabs.evergo.modules.ledger.fragments.LedgerMonthlySummaryListFragment
import com.energolabs.evergo.modules.main.models.MainModel
import com.energolabs.evergo.modules.main.requests.requests.controllers.MainRequestController
import com.energolabs.evergo.modules.main.storage.MainPreferences
import com.energolabs.evergo.modules.settings.fragments.SettingsFragment
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.requests.GetUserRequest
import com.energolabs.evergo.modules.user.profile.requests.PutUserRequest
import com.energolabs.evergo.modules.user.profile.requests.callbacks.AvatarUploaderListener
import com.energolabs.evergo.modules.user.profile.requests.controllers.AvatarUploaderActivityController
import com.energolabs.evergo.modules.user.profile.storage.UserProfilePreferences
import com.energolabs.evergo.modules.user.profile.utils.GenderUtils
import com.energolabs.evergo.ui.activities.BaseActivity
import com.energolabs.evergo.ui.activities.DetailActivityNoCollapsing
import com.energolabs.evergo.utils.BuildUtils
import com.energolabs.evergo.utils.GlideUtils
import com.energolabs.evergo.utils.TextViewAnimUtils
import java.util.*

class MainActivity : BaseActivity(),
        View.OnClickListener,
        AvatarUploaderListener,
        PutUserRequest.PutUserRequestListener {

    private val REQUEST_CAMERA = 100
    private val REQUEST_READ_EXTERNAL_STORAGE = 200
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 300

    private var iv_settings: View? = null
    private var iv_avatar: ImageView? = null

    // ORDER INFO
    private var cpb_order_progress: CircleProgressBar? = null
    private var tv_order_progress: TextView? = null
    private var tv_order_total_energy: TextView? = null
    private var tv_order_transaction_price: TextView? = null
    private var tv_order_type: TextView? = null

    // ENERGY DATA
    private var tv_energy_usage: TextView? = null
    private var tv_energy_creation: TextView? = null
    private var tv_energy_price: TextView? = null

    // SECTIONS ACCESSES
    private var tv_usage: View? = null
    private var tv_market: View? = null
    private var tv_bill: View? = null
    private var tv_ledger: View? = null
    private var tv_devices: View? = null

    private var avatarUploaderActivityController: AvatarUploaderActivityController? = null
    private var mainRequestController: MainRequestController? = null

    private var authPreferences: AuthPreferences? = null
    private var userProfilePreferences: UserProfilePreferences? = null
    private var mainPreferences: MainPreferences? = null

    // ADD ENERGY DEBUG DIALOG
    private var energyDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        authPreferences = AuthPreferences(this)
        userProfilePreferences = UserProfilePreferences(this, authPreferences?.userId)
        mainPreferences = MainPreferences(this, authPreferences?.userId)
        avatarUploaderActivityController = AvatarUploaderActivityController(
                this,
                this
        )
        mainRequestController = MainRequestController(
                this,
                mainResultListener
        )
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun findViews() {
        super.findViews()
        iv_avatar = findViewById(R.id.iv_avatar) as ImageView
        iv_settings = findViewById(R.id.iv_settings)

        cpb_order_progress = findViewById(R.id.cpb_order_progress) as CircleProgressBar
        tv_order_progress = findViewById(R.id.tv_order_progress) as TextView
        tv_order_total_energy = findViewById(R.id.tv_order_total_energy) as TextView
        tv_order_transaction_price = findViewById(R.id.tv_order_transaction_price) as TextView
        tv_order_type = findViewById(R.id.tv_order_type) as TextView

        tv_energy_usage = findViewById(R.id.tv_energy_usage) as TextView
        tv_energy_creation = findViewById(R.id.tv_energy_creation) as TextView
        tv_energy_price = findViewById(R.id.tv_energy_price) as TextView

        tv_usage = findViewById(R.id.tv_usage)
        tv_market = findViewById(R.id.tv_market)
        tv_bill = findViewById(R.id.tv_bill)
        tv_ledger = findViewById(R.id.tv_ledger)
        tv_devices = findViewById(R.id.tv_devices)

        processMainModel(mainPreferences?.mainModel)
    }

    private fun requestUserInfo() {
        GetUserRequest.Builder(this)
                .setUserId(authPreferences?.userId)
                .setResultListener(getUserResultListener)
                .request()
    }

    override fun setupListeners() {
        super.setupListeners()
        iv_avatar?.setOnClickListener(this)
        iv_settings?.setOnClickListener(this)
        tv_usage?.setOnClickListener(this)
        tv_market?.setOnClickListener(this)
        tv_bill?.setOnClickListener(this)
        tv_ledger?.setOnClickListener(this)
        tv_devices?.setOnClickListener(this)

        if (!BuildUtils.isMarketBuild) {
            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensorManager.registerListener(
                    sensorEventListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    internal var sensorEventListener: SensorEventListener = object : SensorEventListener {

        private val SHAKE_THRESHOLD = 1500

        private var lastUpdate: Long = 0
        private var last_x: Float = 0f
        private var last_y: Float = 0f
        private var last_z: Float = 0f

        override fun onSensorChanged(sensorEvent: SensorEvent) {
            val values = sensorEvent.values
            val curTime = System.currentTimeMillis()
            // only allow one update every 100ms.
            if (curTime - lastUpdate > 100) {
                val diffTime = curTime - lastUpdate
                lastUpdate = curTime

                val x = values[SensorManager.DATA_X]
                val y = values[SensorManager.DATA_Y]
                val z = values[SensorManager.DATA_Z]

                val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

                if (speed > SHAKE_THRESHOLD) {
                    onShakeDetected()
                }
                last_x = x
                last_y = y
                last_z = z
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {

        }

        private fun onShakeDetected() {
            if (energyDialog != null) {
                return
            }

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.energo_main_energy_balance)
            builder.setItems(
                    R.array.energo_energy_shake
            ) { dialogInterface, i ->
                when (i) {
                    0 -> {
                        onAddEnergy(5.0)
                        dialogInterface.dismiss()
                    }
                    1 -> {
                        onRemoveEnergy(5.0)
                        dialogInterface.dismiss()
                    }
                    2 -> {
                        onAddEnergy(0.5)
                        dialogInterface.dismiss()
                    }
                    3 -> {
                        onRemoveEnergy(0.5)
                        dialogInterface.dismiss()
                    }
                    4 -> {
                        dialogInterface.dismiss()
                    }
                }
            }
            builder.setOnDismissListener { energyDialog = null }
            energyDialog = builder.show()
        }

        private fun onAddEnergy(amount: Double) {
            val builder = PostEnergyBalanceRequest.Builder(this@MainActivity)
            builder.setSymbol("KWh")
            builder.setType("add")
            builder.setAmount(
                    EnergyController.getRawValue(amount)
            )
            builder.setResultListener(object : GetEnergyBalanceController.ResultListener {
                override fun onResultSuccess(
                        tag: Any?,
                        response: EnergyWalletModel?
                ) {
                    mainRequestController?.makeRequest()
                }

                override fun onResultError(
                        tag: Any?,
                        error: String?,
                        errorCode: Int
                ) {

                }
            })
            builder.request()
            Toast.makeText(
                    this@MainActivity,
                    getString(
                            R.string.energo_energy_wallet_add_energy,
                            amount
                    ),
                    Toast.LENGTH_SHORT
            ).show()
        }

        private fun onRemoveEnergy(amount: Double) {
            val builder = PostEnergyBalanceRequest.Builder(this@MainActivity)
            builder.setSymbol("KWh")
            builder.setType("remove")
            builder.setAmount(
                    EnergyController.getRawValue(amount)
            )
            builder.setResultListener(object : GetEnergyBalanceController.ResultListener {
                override fun onResultSuccess(
                        tag: Any?,
                        response: EnergyWalletModel?
                ) {
                    mainRequestController?.makeRequest()
                }

                override fun onResultError(
                        tag: Any?,
                        error: String?,
                        errorCode: Int
                ) {

                }
            })
            builder.request()
            Toast.makeText(
                    this@MainActivity,
                    getString(
                            R.string.energo_energy_wallet_removed_energy,
                            amount
                    ),
                    Toast.LENGTH_SHORT
            ).show()
        }
    }

    public override fun onResume() {
        super.onResume()
        requestMain()
        updateUserModel(
                userProfilePreferences?.userModel
        )
        requestUserInfo()
    }

    public override fun onPause() {
        super.onPause()
        if (mainRequester != null) {
            mainRequester?.cancel()
            mainRequester = null
        }
    }

    private fun requestMain() {
        mainRequestController?.makeRequest()
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

    private var mainRequester: Timer? = null

    private fun reRunMainRequest() {
        if (mainRequester != null) {
            return
        }

        mainRequester = Timer()
        mainRequester?.schedule(
                object : TimerTask() {
                    override fun run() {
                        // Only request main if activity currently visible
                        if (!isActivityVisible) {
                            return
                        }

                        requestMain()
                        mainRequester = null
                    }
                },
                5000
        )
    }

    private val mainResultListener = object : MainRequestController.MainListener {
        override fun onResultSuccess(
                tag: Any?,
                response: MainModel?
        ) {
            mainPreferences?.saveMainModel(response)
            processMainModel(response)
            reRunMainRequest()
        }

        override fun onResultError(
                tag: Any?,
                error: String?,
                errorCode: Int
        ) {
            // In case of error, use latest known value if available
            processMainModel(
                    mainPreferences?.mainModel
            )
            reRunMainRequest()
        }
    }

    fun openSettings() {
        DetailActivityNoCollapsing.openWithFragment(
                this,
                SettingsFragment::class.java.name,
                SettingsFragment.makeArguments(),
                true
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_avatar -> openSettings()
            R.id.iv_settings -> openSettings()
            R.id.tv_usage -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        HouseChartFragment::class.java.name,
                        HouseChartFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_bill -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        WalletFragment::class.java.name,
                        WalletFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_ledger -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        LedgerMonthlySummaryListFragment::class.java.name,
                        LedgerMonthlySummaryListFragment.makeArguments(),
                        true
                )
            }
            R.id.tv_devices -> {
                DetailActivityNoCollapsing.openWithFragment(
                        this,
                        BatteryListFragment::class.java.name,
                        BatteryListFragment.makeArguments(),
                        true
                )
            }
        }
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

    private fun getDoubleValue(
            textView: TextView?
    ): Double {
        return java.lang.Double.valueOf(
                textView?.text?.toString()
        )
    }

    override fun onResultSuccess(
            tag: Any?,
            response: UserModel?
    ) {
        requestUserInfo()
    }

    private fun processMainModel(mainModel: MainModel?) {
        TextViewAnimUtils.updateNumberValue(
                this,
                tv_energy_creation,
                getDoubleValue(
                        tv_energy_creation
                ),
                EnergyController.getRealValue(mainModel?.energyCreated ?: 0),
                R.string.energo_energy_format
        )
        TextViewAnimUtils.updateNumberValue(
                this,
                tv_energy_usage,
                getDoubleValue(
                        tv_energy_usage
                ),
                EnergyController.getRealValue(mainModel?.energyUsed ?: 0),
                R.string.energo_energy_format
        )
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

package com.sparksign.linfo_pl

import android.app.Activity
import android.app.Application
import android.app.admin.DevicePolicyManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.LifecycleObserver
import com.sparksign.linfo_pl.battery.BatteryHandlerImpl
import com.sparksign.linfo_pl.battery.BatteryProtocol
import com.sparksign.linfo_pl.bluetooth.BluetoothBroadcastReceiver
import com.sparksign.linfo_pl.bluetooth.BluetoothProtocol
import com.sparksign.linfo_pl.lifecycle.ActivityLifecycleObserver
import com.sparksign.linfo_pl.network.NetworkBroadcastReceiver
import com.sparksign.linfo_pl.network.NetworkProtocol
import com.sparksign.linfo_pl.permission.OperationOnPermission
import com.sparksign.linfo_pl.permission.PermissionsHandlerImpl
import com.sparksign.linfo_pl.permission.ServiceManager
import com.sparksign.linfo_pl.policy.DeviceAdmin
import com.sparksign.linfo_pl.policy.PolicyProtocol
import com.sparksign.linfo_pl.policy.PolicyUtil
import com.sparksign.linfo_pl.power.PowerHandlerImpl
import com.sparksign.linfo_pl.power.PowerProtocol
import com.sparksign.linfo_pl.power.thermal.ThermalStatusHandlerImpl
import com.sparksign.linfo_pl.power.wakelock.WakeLockStateHandlerImpl
import com.sparksign.linfo_pl.window.WindowHandlerImpl
import com.sparksign.linfo_pl.window.WindowProtocol
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
import io.flutter.plugin.common.StandardMethodCodec


/** LinfoPlPlugin */
class LinfoPlPlugin:
  FlutterPlugin,
  ActivityAware,
//  ActivityResultListener,
//  RequestPermissionsResultListener,
  LifecycleObserver {
  private var intent:                             Intent?                     = null
  private var context:                            Context?                    = null
  private var messenger:                          BinaryMessenger?            = null

  private var activity:                           Activity?                   = null
  private val activityResult:                     MethodChannel.Result?       = null
  private var activityPluginBinding:              ActivityPluginBinding?      = null

  private var serviceManager:                     ServiceManager?             = null
  private var batteryManager:                     BatteryManager?             = null
  private var powerManager:                       PowerManager?               = null
  private val bluetoothAdapter:                   BluetoothAdapter?           = null
  private var bluetoothManager:                   BluetoothManager?           = null
  private var windowManager:                      WindowManager?              = null

  private var permissionResult:                   Boolean?                    = null

  private lateinit var channel:                   MethodChannel

  private lateinit var networkEventChannel:       EventChannel
  private lateinit var bluetoothEventChannel:     EventChannel
  private lateinit var batteryEventChannel:       EventChannel
  private lateinit var powerEventChannel:         EventChannel
  private lateinit var powerThermalEventChannel:  EventChannel
  private lateinit var wakeLockEventChannel:      EventChannel
  private lateinit var windowEventChannel:        EventChannel

  private lateinit var networkProtocol:           NetworkProtocol
  private lateinit var bluetoothProtocol:         BluetoothProtocol
  private lateinit var batteryProtocol:           BatteryProtocol
  private lateinit var powerProtocol:             PowerProtocol
  private lateinit var windowProtocol:            WindowProtocol
  private lateinit var policyProtocol:            PolicyProtocol

  private lateinit var networkReceiver:           NetworkBroadcastReceiver
  private lateinit var bluetoothReceiver:         BluetoothBroadcastReceiver
  private lateinit var batteryReceiver:           BatteryHandlerImpl
  private lateinit var powerReceiver:             PowerHandlerImpl
  private lateinit var powerThermalReceiver:      ThermalStatusHandlerImpl
  private lateinit var wakeLockReceiver:          WakeLockStateHandlerImpl
  private lateinit var windowReceiver:            WindowHandlerImpl
  private lateinit var permissionsHandlerImpl:    PermissionsHandlerImpl


  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val taskQueue = flutterPluginBinding.binaryMessenger.makeBackgroundTaskQueue()
    messenger     = flutterPluginBinding.binaryMessenger
    context       = flutterPluginBinding.applicationContext
    channel       = MethodChannel(
      messenger!!,
      METHOD_CHANNEL_NAME,
      StandardMethodCodec.INSTANCE,
      taskQueue
    )
//    setupMethodChannel(flutterPluginBinding.binaryMessenger, flutterPluginBinding.applicationContext)
  }

  private fun setupMethodChannel(messenger: BinaryMessenger, context: Context) {
    val connectivityManager     : ConnectivityManager?
    val wifiManager             : WifiManager?
    val telephonyManager        : TelephonyManager?
    val handler                 : MethodCallHandlerImpl?
    val devicePolicyManager     : DevicePolicyManager?
    val componentName           : ComponentName?

    componentName = ComponentName(context, DeviceAdmin::class.java)

//  if (permissionResult == true) {

    connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    wifiManager =
      context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    telephonyManager =
      context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    devicePolicyManager =
      context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    bluetoothManager =
      context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    powerManager =
      context.getSystemService(Application.POWER_SERVICE) as PowerManager

    windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      batteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    } else {
      intent = ContextWrapper(context).registerReceiver(null, IntentFilter(
        Intent.ACTION_BATTERY_CHANGED)
      )
    }


    serviceManager = ServiceManager(bluetoothManager!!, context)


    val bluetoothAdapter: BluetoothAdapter = bluetoothManager?.adapter!!

//    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    networkProtocol =
      NetworkProtocol(wifiManager, connectivityManager, telephonyManager)

    bluetoothProtocol =
      BluetoothProtocol(bluetoothAdapter, permissionsHandlerImpl, serviceManager!!)

    batteryProtocol =
      BatteryProtocol(context, batteryManager, intent)

    powerProtocol =
      PowerProtocol(context, powerManager)

    windowProtocol = WindowProtocol(context, windowManager, activity!!)

    policyProtocol = PolicyProtocol(context, activity!!, componentName, devicePolicyManager!!)

    handler = MethodCallHandlerImpl(
//    context,
      networkProtocol,
      bluetoothProtocol,
      batteryProtocol,
      powerProtocol,
      windowProtocol,
      policyProtocol,
//    bluetoothAdapter,
      activityPluginBinding!!,
      permissionsHandlerImpl
    )

    channel.setMethodCallHandler(handler)

    setupEventChannels(messenger, context)
//    } else {
//      Log.e("LinfoPL", "Permission result: $permissionResult")
//    }

//    val filterAdapter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
//    context.registerReceiver(BluetoothBroadcastReceiver(context, bluetoothProtocol), filterAdapter)
  }

  private fun setupEventChannels(messenger: BinaryMessenger, context: Context) {
    networkEventChannel       = EventChannel(messenger, NETWORK_EVENT_NAME)
    networkReceiver           = NetworkBroadcastReceiver(context, networkProtocol)

    bluetoothEventChannel     = EventChannel(messenger, BLUETOOTH_CHANNEL_NAME)
    bluetoothReceiver         = BluetoothBroadcastReceiver(context, bluetoothProtocol, permissionsHandlerImpl)

    batteryEventChannel       = EventChannel(messenger, BATTERY_CHANNEL_NAME)
    batteryReceiver           = BatteryHandlerImpl(context, batteryProtocol)

    powerEventChannel         = EventChannel(messenger, POWER_CHANNEL_NAME)
    powerReceiver             = PowerHandlerImpl(context, powerProtocol, powerManager)

    powerThermalEventChannel  = EventChannel(messenger, POWER_THERMAL_CHANNEL_NAME)
    powerThermalReceiver      = ThermalStatusHandlerImpl(powerManager)

    wakeLockEventChannel      = EventChannel(messenger, WAKELOCK_CHANNEL_NAME)
    wakeLockReceiver          = WakeLockStateHandlerImpl(context, powerProtocol)

    windowEventChannel        = EventChannel(messenger, WINDOW_CHANNEL_NAME)
    windowReceiver            = WindowHandlerImpl(context, windowProtocol)

    networkEventChannel.setStreamHandler(networkReceiver)
    bluetoothEventChannel.setStreamHandler(bluetoothReceiver)
    batteryEventChannel.setStreamHandler(batteryReceiver)
    powerEventChannel.setStreamHandler(powerReceiver)
    powerThermalEventChannel.setStreamHandler(powerThermalReceiver)
    wakeLockEventChannel.setStreamHandler(wakeLockReceiver)
    windowEventChannel.setStreamHandler(windowReceiver)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    networkEventChannel.setStreamHandler(null)
    bluetoothEventChannel.setStreamHandler(null)
    batteryEventChannel.setStreamHandler(null)
    powerEventChannel.setStreamHandler(null)
    powerThermalEventChannel.setStreamHandler(null)
    wakeLockEventChannel.setStreamHandler(null)
    windowEventChannel.setStreamHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
//    binding.activity.window.addFlags(
//      WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//              or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//              or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//    )

    this.activityPluginBinding  = binding
    this.activity               = binding.activity

    permissionsHandlerImpl = context?.let {
      PermissionsHandlerImpl(activityPluginBinding!!, it, activityResult)
    }!!

    binding.addActivityResultListener(permissionsHandlerImpl)
    binding.addRequestPermissionsResultListener(permissionsHandlerImpl)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      activity!!.registerActivityLifecycleCallbacks(ActivityLifecycleObserver(channel))
    }

//    (activityPluginBinding!!.lifecycle as HiddenLifecycleReference)
//      .lifecycle
//      .addObserver(LifecycleEventObserver { source, event ->
//        Log.e("Activity state: ", "$event-$source")
//      })

//    activityPluginBinding!!.addActivityResultListener(this)

//    permissionResult = permissionsHandlerImpl.checkPermissions()

    messenger?.let { context?.let { it1 -> setupMethodChannel(it, it1) } }

//    BluetoothPermissionHandlerImpl(binding, activeContext, activityResult, bluetoothAdapter)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    this.activityPluginBinding  = null
    this.activity               = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    this.activityPluginBinding  = binding
    this.activity               = binding.activity

    binding.addActivityResultListener(permissionsHandlerImpl)
  }

  override fun onDetachedFromActivity() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      activity!!.unregisterActivityLifecycleCallbacks(ActivityLifecycleObserver(channel))
    }

    activityPluginBinding?.removeActivityResultListener(permissionsHandlerImpl)
    activityPluginBinding?.removeRequestPermissionsResultListener(permissionsHandlerImpl)

    this.activityPluginBinding  = null
    this.activity               = null
  }

//  override fun onRequestPermissionsResult(
//    requestCode: Int,
//    permissions: Array<String?>,
//    grantResults: IntArray
//  ): Boolean {
//    Log.i("Permission", "onRequestPermissionsResult Girdi")
//    val operation: OperationOnPermission = permissionsHandlerImpl.operationsOnPermission[requestCode]!!
//    return if (operation != null && grantResults.isNotEmpty()) {
//      operation.op(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0])
//      Log.i("Permission", "onRequestPermissionsResult true")
//      true
//    } else {
//      Log.i("Permission", "onRequestPermissionsResult false")
//      false
//    }
//  }

  private fun invokeMethodUIThread(method: String, data: HashMap<String, Any>) {
    Handler(Looper.getMainLooper()).post {
      //Could already be teared down at this moment
      if (channel != null) {
        channel.invokeMethod(method, data)
      } else {
        Log.i("[LinfoPL]",
          "invokeMethodUIThread: tried to call method on closed channel: $method")
      }
    }
  }

  inline fun <T:Any, R> whenNotNull(input: T?, callback: (T)->R): R? {
    return input?.let(callback)
  }

  companion object {
    private const val METHOD_CHANNEL_NAME =
      "com.sparksign.linfo_pl/methods"
    private const val NETWORK_EVENT_NAME =
      "com.sparksign.linfo_pl/network"
    private const val BLUETOOTH_CHANNEL_NAME =
      "com.sparksign.linfo_pl/bluetooth"
    private const val BATTERY_CHANNEL_NAME =
      "com.sparksign.linfo_pl/battery"
    private const val POWER_CHANNEL_NAME =
      "com.sparksign.linfo_pl/power"
    private const val POWER_THERMAL_CHANNEL_NAME =
      "com.sparksign.linfo_pl/powerThermal"
    private const val WAKELOCK_CHANNEL_NAME =
      "com.sparksign.linfo_pl/wakeLock"
    private const val WINDOW_CHANNEL_NAME =
      "com.sparksign.linfo_pl/window"
  }
}

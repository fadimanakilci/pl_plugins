/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import com.sparksign.linfo_pl.battery.BatteryProtocol
import com.sparksign.linfo_pl.bluetooth.BluetoothProtocol
import com.sparksign.linfo_pl.lifecycle.ActivityLifecycleProtocol
import com.sparksign.linfo_pl.network.NetworkProtocol
import com.sparksign.linfo_pl.permission.PermissionsHandlerImpl
import com.sparksign.linfo_pl.policy.PolicyProtocol
import com.sparksign.linfo_pl.power.PowerProtocol
import com.sparksign.linfo_pl.window.WindowProtocol
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MethodCallHandlerImpl(
//    private val context:              Context,
    private val networkProtocol:        NetworkProtocol,
    private val bluetoothProtocol:      BluetoothProtocol,
    private val batteryProtocol:        BatteryProtocol,
    private val powerProtocol:          PowerProtocol,
    private val windowProtocol:         WindowProtocol,
    private val policyProtocol:         PolicyProtocol,
//    private val bluetoothAdapter:     BluetoothAdapter,
    private val activityPluginBinding:  ActivityPluginBinding,
    private val permissionsHandlerImpl: PermissionsHandlerImpl
) : MethodChannel.MethodCallHandler  {

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val repeat          : Int?      = call.argument<Int?>("repeat")
        val minutes         : Double?   = call.argument<Double?>("minutes")
        val waitMinutes     : Double?   = call.argument<Double?>("waitMinutes")
        val brightness      : Float?    = call.argument<Double?>("brightness")?.toFloat()

        when(call.method) {
            // NETWORK
            "networkStatus"         -> result.success(networkProtocol.getNetworkTypes())
            "networkInfo"           -> result.success(networkProtocol.getNetworkInfo())
            "requestOnMobile"       -> result.success(networkProtocol.stateOnMobile())

            // BLUETOOTH  - Tiramisudan sonrasında state her türlü false dönecek
//            "bluetoothState"        -> result.success(bluetoothProtocol.getState())
            "bluetoothState"        -> {
                val permissions = ArrayList<String>()

                if (Build.VERSION.SDK_INT >= 31) { // Android 12 (October 2021)
                    permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
                }

                if (Build.VERSION.SDK_INT <= 30) { // Android 11 (September 2020)
                    permissions.add(Manifest.permission.BLUETOOTH)
                }

//                permissionsHandlerImpl.ensurePermissions(permissions) { granted, perm ->
//                    if (granted === false) {
//                        result.error(
//                            "turnOn",
//                            java.lang.String.format("FlutterBluePlus requires %s permission", perm),
//                            null
//                        )

//                        return@ensurePermissions
//                    }
                    result.success(bluetoothProtocol.getState())
//                }
            }
            "bluetoothStateOn"      -> result.success(bluetoothProtocol.stateOn(activityPluginBinding))
            "bluetoothStateOff"     -> result.success(bluetoothProtocol.stateOff(activityPluginBinding))

            // LIFECYCLE
            "activityLifecycle"     -> result.success(ActivityLifecycleProtocol().getState(activityPluginBinding))

            // BATTERY
            "batteryLevel"          -> result.success(batteryProtocol.getBatteryLevel())

            // POWER
            "interactive"           -> result.success(powerProtocol.getPowerInteractive())
            "powerThermalStatus"    -> result.success(powerProtocol.getPowerThermalStatus())

            "wakeLockEnable"        -> result.success(powerProtocol.enableWakeLock())
            "wakeLockDisable"       -> result.success(powerProtocol.disableWakeLock())
            "wakeLockState"         -> result.success(powerProtocol.getWakeLockState())
            "wakeLockScheduler"     -> result.success(powerProtocol.enableSchedulerWakeLock(minutes))
            "wakeUpScheduler"       -> result.success(powerProtocol.enableSchedulerWakeUp(minutes))
            "wakeLockPeriodic"      -> result.success(powerProtocol.enablePeriodicWakeLock(repeat, minutes, waitMinutes))

            // WINDOW
            "keepScreenOn"          -> windowProtocol.keepScreenOn { result.success(it) }
//            "keepScreenOn"          -> result.success(windowProtocol.keepScreenOn())
            "discardScreenOn"       -> result.success(runBlocking{ windowProtocol.discardScreenOn() })
            "screenOn"              -> result.success(windowProtocol.getScreenOn())
//            "schedulerWakeup"       -> result.success(windowProtocol.schedulerWakeUp(minutes))
            "changeBrightness"      -> result.success(windowProtocol.changeBrightness(brightness))
            "requestKeyguard"       -> windowProtocol.requestKeyguard { result.success(it) }

            "orientationState"      -> result.success(windowProtocol.getOrientationState())

            // POLICY
            "turnOffScreen"         -> result.success(policyProtocol.turnOffScreen())

            else                    -> result.notImplemented()
        }
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.power

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import kotlinx.coroutines.runBlocking

class PowerHandlerImpl(
    private var context: Context,
    private var powerProtocol: PowerProtocol,
    private var powerManager: PowerManager?,
): Power, StreamHandler {
    private var events: EventSink? = null
    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var powerBroadcastReceiver: PowerBroadcastReceiver

    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events
//        runBlocking {  }
        sendEvent(null)
        powerBroadcastReceiver = PowerBroadcastReceiver {
//           runBlocking {  }
            sendEvent(it)
        }

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                addAction(PowerManager.ACTION_DEVICE_LIGHT_IDLE_MODE_CHANGED)
                addAction(PowerManager.ACTION_LOW_POWER_STANDBY_ENABLED_CHANGED)
            }
        }
        context.registerReceiver(powerBroadcastReceiver, filter)
    }

    override fun onCancel(arguments: Any?) {
        Log.i("LinfoPL", "onCancel Girdi")
        events = null
        context.unregisterReceiver(powerBroadcastReceiver)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            powerManager!!.removeThermalStatusListener(powerProtocol.powerChangeListener)
//        }
    }

    override fun sendEvent(data: Intent?) {
        val _data = mapOf<String, Any?>(
            "interactive"    to powerProtocol.getPowerInteractive(),
//            "thermalStatus"  to powerProtocol.getPowerThermalStatus(),
        )
        events!!.success(_data)
    }

//    override fun sendEvent(data: Any?) {
//        var thermalStatus: String? = null
//        Log.i("POWERMANAGER", "Result 1: ${thermalStatus.toString()}")
//        powerProtocol.getPowerThermalStatus {
//            Log.i("POWERMANAGER", "Result: $it")
//            thermalStatus = it
//            val data = mapOf<String, Any?>(
//                "interactive"    to powerProtocol.getPowerInteractive(),
//                "thermalStatus"  to thermalStatus
//            )
//            events!!.success(data)
//        }
//    }
}
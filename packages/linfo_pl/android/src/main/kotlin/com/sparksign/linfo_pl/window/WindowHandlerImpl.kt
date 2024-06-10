/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.window

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.sparksign.linfo_pl.power.PowerUtil
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler

class WindowHandlerImpl(
    private var context: Context,
    private var windowProtocol: WindowProtocol
): Window, StreamHandler {
    private var events: EventSink? = null
    private lateinit var windowBroadcastReceiver: WindowBroadcastReceiver

    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events
        sendEvent(null)
        windowBroadcastReceiver = WindowBroadcastReceiver {
            sendEvent(it)
        }
        context.registerReceiver(windowBroadcastReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        })
    }

    override fun onCancel(arguments: Any?) {
        events = null
        context.unregisterReceiver(windowBroadcastReceiver)
    }

    override fun sendEvent(data: Intent?) {
        Log.d(PowerUtil.LOG_TAG, "Window event: $data")
        val _data = mapOf<String, Any?>(
            "screenOn"    to windowProtocol.getScreenOn(),
//            "thermalStatus"  to powerProtocol.getPowerThermalStatus(),
        )
        events!!.success(_data)
    }
}
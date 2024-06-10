/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.power.thermal

import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.sparksign.linfo_pl.power.PowerUtil
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler

class ThermalStatusHandlerImpl(
    private var powerManager: PowerManager?,
): Thermal, StreamHandler {
    private var events: EventSink? = null

    private val powerChangeListener = PowerManager.OnThermalStatusChangedListener {
        Log.d(PowerUtil.LOG_TAG, "Thermal status changed: $it")
        sendEvent(it)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events
        powerManager!!.addThermalStatusListener(powerChangeListener)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCancel(arguments: Any?) {
        events = null
        powerManager!!.removeThermalStatusListener(powerChangeListener)
    }

    override fun sendEvent(data: Int?) {
        events!!.success(PowerUtil.getThermalStatus(data))
    }
}
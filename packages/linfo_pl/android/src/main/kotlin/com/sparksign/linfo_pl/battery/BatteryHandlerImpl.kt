/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler

class BatteryHandlerImpl(
    private var context: Context,
    private var batteryProtocol: BatteryProtocol,
): Battery, StreamHandler {
    private var events: EventSink? = null
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private lateinit var batteryReceiver: BatteryBroadcastReceiver

    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events
        batteryReceiver = BatteryBroadcastReceiver {
            sendEvent(it)
        }
        context.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onCancel(arguments: Any?) {
        events = null
        context.unregisterReceiver(batteryReceiver)
    }

    override fun sendEvent(intent: Intent) {
        val data = mapOf<String, Any?>(
            "batteryLevel"      to batteryProtocol.getBatteryLevel(),
            "charging"          to batteryProtocol.getBatteryChargingStatus(intent),
            "plugged"           to batteryProtocol.getBatteryPlugged(intent),
            "health"            to batteryProtocol.getBatteryHealth(intent),
            "temperature"       to batteryProtocol.getBatteryTemperature(intent),
            "isLow"             to batteryProtocol.getBatteryLow(intent),
            "technology"        to batteryProtocol.getBatteryTechnology(intent),
            "level"             to batteryProtocol.getBatteryLevel(intent),
            "scale"             to batteryProtocol.getBatteryScale(intent),
            "present"           to batteryProtocol.getBatteryPresent(intent),
            "voltage"           to batteryProtocol.getBatteryVoltage(intent),
            "chargeCounter"     to batteryProtocol.getBatteryPropertyChargeCounter(),
            "currentNow"        to batteryProtocol.getBatteryPropertyCurrentNow(),
            "currentAverage"    to batteryProtocol.getBatteryPropertyCurrentAverage(),
            "capacity"          to batteryProtocol.getBatteryPropertyCapacity(),
            "energyCounter"     to batteryProtocol.getBatteryPropertyEnergyCounter(),
            "status"            to batteryProtocol.getBatteryPropertyStatus(),
            "isCharging"        to batteryProtocol.getBatteryIsCharging(),
            "chargeTime"        to batteryProtocol.getBatteryChargeTimeRemaining(),
        )
        events!!.success(data)
    }
}
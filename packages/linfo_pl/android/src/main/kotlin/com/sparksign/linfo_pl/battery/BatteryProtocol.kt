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

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class BatteryProtocol (
    private val context: Context,
    private var batteryManager: BatteryManager?,
    private var intent: Intent?,
){
    /// Battery Level
    fun getBatteryLevel(): Int? {
        val batteryLevel: Int
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            batteryLevel = batteryManager!!.getIntProperty(BATTERY_PROPERTY_CAPACITY)
        } else {
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) *
                    100 / intent!!.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }

        return if(batteryLevel != -1) {
            batteryLevel
        } else {
            Log.e("UNAVAILABLE", "Battery level not available.")
            null
        }
    }

    /// EXTRA

    /// Current Battery Charging Status
    fun getBatteryChargingStatus(intent : Intent): String {
        return BatteryUtil.getBatteryStatus(
            intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1))
    }

    /// Which Power Source The Battery Plugged in
    fun getBatteryPlugged(intent: Intent): String {
        return BatteryUtil.getBatteryPlugged(
            intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1))
    }

    /// Current Battery Health Status
    fun getBatteryHealth(intent: Intent): String {
        return BatteryUtil.getBatteryHealth(
            intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1))
    }

    /// Current Battery Temperature °C
    fun getBatteryTemperature(intent: Intent): Double {
//      return roundOffDecimal(
//          intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toDouble() / 10)
        return intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1).toDouble() / 10
    }

    /// Whether is Currently Battery Low
    fun getBatteryLow(intent: Intent): Boolean? {
        return if (VERSION.SDK_INT >= VERSION_CODES.P) {
            intent.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW, false)
        } else {
            null
        }
    }

    /// The Technology of The Current Battery
    fun getBatteryTechnology(intent: Intent): String? {
        return intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
    }

    /// Current Battery Level, From 0 to Scale - ex. 99
    fun getBatteryLevel(intent: Intent): Int {
        return intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    }

    /// Max Battery Level - ex. 100
    fun getBatteryScale(intent: Intent): Int {
        return intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }

    /// Whether Battery is Present
    fun getBatteryPresent(intent: Intent): Boolean {
        return intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
    }

    /// Current Battery Voltage Level
    fun getBatteryVoltage(intent: Intent): Int {
        return intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)
    }

    /// PROPERTY

    /// Battery Capacity in microampere/hours -> ampere/hours
    fun getBatteryPropertyChargeCounter(): Double? {
        return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            formatMicroampereToAmpere(
                batteryManager?.getIntProperty(BATTERY_PROPERTY_CHARGE_COUNTER))
        } else {
            null
        }
    }

    /// Battery Current in microamperes
    fun getBatteryPropertyCurrentNow(): Double? {
        return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            formatMicroampereToAmpere(
                batteryManager?.getIntProperty(BATTERY_PROPERTY_CURRENT_NOW))
        } else {
            null
        }
    }

    /// Average Battery Current in microamperes
    fun getBatteryPropertyCurrentAverage(): Double? {
        return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
           formatMicroampereToAmpere(
               batteryManager?.getIntProperty(BATTERY_PROPERTY_CURRENT_AVERAGE))
        } else {
            null
        }
    }

    ///  Same Battery Level - ex. 99
    fun getBatteryPropertyCapacity(): Int? {
        return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            batteryManager?.getIntProperty(BATTERY_PROPERTY_CAPACITY)
        } else {
            null
        }
    }

    /// Battery Remaining Energy in nanowatt/hours
    fun getBatteryPropertyEnergyCounter(): Double? {
        return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            formatNanowattToWatt(batteryManager?.getLongProperty(BATTERY_PROPERTY_ENERGY_COUNTER))
        } else {
            null
        }
    }

    /// Same Current Battery Status
    fun getBatteryPropertyStatus(): Int? {
        return if (VERSION.SDK_INT >= VERSION_CODES.O) {
            batteryManager?.getIntProperty(BATTERY_PROPERTY_STATUS)
        } else {
            null
        }
    }

    /// Whether Battery is Charging
    fun getBatteryIsCharging(): Boolean? {
        return if (VERSION.SDK_INT >= VERSION_CODES.M) {
            batteryManager?.isCharging
        } else {
            null
        }
    }

    /// How Much miliseconds Battery is Fully Charged
    fun getBatteryChargeTimeRemaining(): String? {
        return if (VERSION.SDK_INT >= VERSION_CODES.P) {
            formatMilliseconds(batteryManager?.computeChargeTimeRemaining()!!)
        } else {
            null
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        Log.i("LinfoPL", "Thermal Value: $number")
        number.toBigDecimal().setScale(1, RoundingMode.CEILING).toDouble()
        Log.i("LinfoPL", "Thermal Value: $number")
        return number
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatMilliseconds(milliseconds: Long?): String? {
        return if(milliseconds != null) {
    //            val totalSeconds = milliseconds / 1000
    //            "${totalSeconds / 60}:${totalSeconds % 60}"
            "${TimeUnit.MILLISECONDS.toHours(milliseconds)}" +
                    ":${TimeUnit.MILLISECONDS.toMinutes(milliseconds) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds))}" +
                    ":${TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))}"
        } else {
            null
        }
    }

    private fun formatMicroampereToAmpere(microampere: Int?): Double? {
        return microampere?.toDouble()?.div(1000000)
    }

    private fun formatNanowattToWatt(nanowatt: Long?): Double? {
        return BigDecimal(nanowatt!!)
            .divide(1000000000.toBigDecimal(), 4, RoundingMode.CEILING)
            .toDouble()
    }

    companion object {
        private const val BATTERY_PROPERTY_CHARGE_COUNTER       = 1
        private const val BATTERY_PROPERTY_CURRENT_NOW          = 2
        private const val BATTERY_PROPERTY_CURRENT_AVERAGE      = 3
        private const val BATTERY_PROPERTY_CAPACITY             = 4
        private const val BATTERY_PROPERTY_ENERGY_COUNTER       = 5
        private const val BATTERY_PROPERTY_STATUS               = 6
    }
}
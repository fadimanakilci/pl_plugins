/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.permission

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.location.LocationManager
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.provider.Settings
import android.text.TextUtils
import android.util.Log


class ServiceManager(
    private val bluetoothManager: BluetoothManager,
    private val context: Context
) {
    fun checkServiceStatus(
        permission: Int,
//        successCallback: SuccessCallback,
//        errorCallback: ErrorCallback,
    ) : Int? {
        if (context == null) {
            Log.d(PermissionConstant.LOG_TAG, "Context cannot be null.")
//            errorCallback.onError(
//                "PermissionHandler.ServiceManager",
//                "Android context cannot be null."
//            )
            return null
        }
        if (permission == PermissionConstant.PERMISSION_GROUP_LOCATION || permission == PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS || permission == PermissionConstant.PERMISSION_GROUP_LOCATION_WHEN_IN_USE) {
            val serviceStatus: Int =
                if (isLocationServiceEnabled(context)) PermissionConstant.SERVICE_STATUS_ENABLED else PermissionConstant.SERVICE_STATUS_DISABLED
            return serviceStatus

        }
        if (permission == PermissionConstant.PERMISSION_GROUP_BLUETOOTH) {
            val serviceStatus: Int =
                if (isBluetoothServiceEnabled(context)) PermissionConstant.SERVICE_STATUS_ENABLED else PermissionConstant.SERVICE_STATUS_DISABLED
            return serviceStatus
        }
        if (permission == PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS) {
            val serviceStatus: Int =
                if (VERSION.SDK_INT >= VERSION_CODES.M) PermissionConstant.SERVICE_STATUS_ENABLED else PermissionConstant.SERVICE_STATUS_NOT_APPLICABLE
            return serviceStatus
        }
        return PermissionConstant.SERVICE_STATUS_NOT_APPLICABLE
    }

    private fun isLocationServiceEnabled(context: Context): Boolean {
        return if (VERSION.SDK_INT >= VERSION_CODES.P) {
            val locationManager = context.getSystemService(
                LocationManager::class.java
            ) ?: return false
            locationManager.isLocationEnabled
        } else
            isLocationServiceEnabledKitKat(context)
    }

    // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
    // pre S versions of Android
    private fun isBluetoothServiceEnabled(context: Context): Boolean {
        val adapter = bluetoothManager.adapter
        return adapter.isEnabled
    }

    // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
    // pre TIRAMISU versions of Android
    private fun getCallAppsList(pm: PackageManager): List<ResolveInfo> {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:123123")
        return if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(callIntent, PackageManager.ResolveInfoFlags.of(0))
        } else {
            pm.queryIntentActivities(callIntent, 0)
        }
    }

    companion object {
        // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
        // pre Pie versions of Android.
        private fun isLocationServiceEnabledKitKat(context: Context): Boolean {
            val locationMode: Int
            locationMode = try {
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF
        }

        // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
        // pre KitKat versions of Android.
        private fun isLocationServiceEnablePreKitKat(context: Context): Boolean {
            return false
            val locationProviders: String = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            return !TextUtils.isEmpty(locationProviders)
        }
    }
}
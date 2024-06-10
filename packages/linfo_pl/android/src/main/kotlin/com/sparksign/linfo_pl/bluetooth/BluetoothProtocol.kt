/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.util.Log
import com.sparksign.linfo_pl.permission.PermissionConstant
import com.sparksign.linfo_pl.permission.PermissionsHandlerImpl
import com.sparksign.linfo_pl.permission.ServiceManager
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding


class BluetoothProtocol(
    private val bluetoothAdapter: BluetoothAdapter,
    private val permissionsHandlerImpl: PermissionsHandlerImpl,
    private val serviceManager: ServiceManager
) {
    @SuppressLint("SuspiciousIndentation")
    fun getState(): String {
//        val permissions = ArrayList<String>()
//
//        if (Build.VERSION.SDK_INT >= 31) {
//            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
//        } else if (Build.VERSION.SDK_INT <= 30) {
//            permissions.add(Manifest.permission.BLUETOOTH)
//        }
//
//        val result: Boolean? = permissionsHandlerImpl.askedPermissions(permissions)

//        if(result == true) {
        val permission: Int = 21
        val permissions: List<Int> = listOf(21)
        val result: Int = permissionsHandlerImpl.checkPermissionStatus(permission)
//        if(result == 0) {
            val a = permissionsHandlerImpl.requestPermissions(permissions)
            Log.i(PermissionConstant.LOG_TAG, "Test If: $a")
//        } else {
//            Log.i(PermissionConstant.LOG_TAG, "Test: $result")
//            return adapterStateString(bluetoothAdapter.state)
//        }
//        val result: Int? = serviceManager.checkServiceStatus(permission)
            Log.i(PermissionConstant.LOG_TAG, "Test: $result")
            return adapterStateString(bluetoothAdapter.state)
//        } else {
//            return "UNKNOWN"
//        }

    }

    fun stateOn(
        activityPluginBinding: ActivityPluginBinding
    ): Boolean {
        val enableBluetoothRequestCode: Int = 187

        Log.i("GİRDİİİİİ", "stateOn")

        if (bluetoothAdapter.isEnabled) {
            return true
        }

        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

        activityPluginBinding.activity
            .startActivityForResult(enableBtIntent, enableBluetoothRequestCode)

        return true
    }

    fun stateOff(
        activityPluginBinding: ActivityPluginBinding
    ): Boolean {
        val enableBluetoothRequestCode: Int = 1879

        Log.i("GİRDİİİİİ", "stateOff")

        if (!bluetoothAdapter.isEnabled) {
            return true
        }

        bluetoothAdapter.disable()

        return true
    }

    companion object {
        @JvmStatic
        fun adapterStateEnum(`as`: Int): Int {
            return when (`as`) {
                BluetoothAdapter.STATE_OFF          -> 10
                BluetoothAdapter.STATE_TURNING_ON   -> 11
                BluetoothAdapter.STATE_ON           -> 12
                BluetoothAdapter.STATE_TURNING_OFF  -> 13
                else                                -> 0
            }
        }

        @JvmStatic
        private fun adapterStateString(`as`: Int): String {
            return when (`as`) {
                BluetoothAdapter.STATE_OFF          -> "off"
                BluetoothAdapter.STATE_TURNING_ON   -> "turningOn"
                BluetoothAdapter.STATE_ON           -> "on"
                BluetoothAdapter.STATE_TURNING_OFF  -> "turningOff"
                else                                -> "UNKNOWN_ADAPTER_STATE ($`as`)"
            }
        }
    }
}
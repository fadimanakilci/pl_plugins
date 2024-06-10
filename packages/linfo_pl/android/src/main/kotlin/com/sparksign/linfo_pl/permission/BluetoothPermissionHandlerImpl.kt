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

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry


// TODO: Permission yapılandırmasını yap
class BluetoothPermissionHandlerImpl(
    private val binding: ActivityPluginBinding,
    private var context: Context?,
    private var result: Result?,
    // private var bluetoothAdapter: BluetoothAdapter?,
) : Permission {
    private var activity: Activity = binding.activity
    // private val bluetoothManager: BluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//    private var pendingPermissionsEnsureCallbacks: EnsurePermissionsCallback? = null
    var operationOnPermission: OperationOnPermission? = null



//    fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ): Boolean {
//        val operation: OperationOnPermission? = operationsOnPermission.get(requestCode)
//        return if (operation != null && grantResults.isNotEmpty()) {
//            operation.op(
//                grantResults[0] == PackageManager.PERMISSION_GRANTED,
//                permissions[0]
//            )
//            true
//        } else {
//            false
//        }
//    }

    override fun addResultListener()/*: Activity*/ {
        binding.addActivityResultListener { requestCode, resultCode, data ->
            when (requestCode) {
                REQUEST_ENABLE_BLUETOOTH -> {
                    // @TODO - used underlying value of `Activity.RESULT_CANCELED` since we tend to use `androidx` in which I were not able to find the constant.
                    result?.success(resultCode != 0)
                    return@addActivityResultListener true
                }

                REQUEST_DISCOVERABLE_BLUETOOTH -> {
                    result?.success(if (resultCode == 0) -1 else resultCode)
                    return@addActivityResultListener true
                }

                else -> return@addActivityResultListener false
            }
        }
        binding.addRequestPermissionsResultListener { requestCode, permissions, grantResults ->
            when (requestCode) {
                REQUEST_COARSE_LOCATION_PERMISSIONS -> {
                    Log.i("Permission", "Test: Listener")
                    operationOnPermission?.op(grantResults[0] == PackageManager.PERMISSION_GRANTED, null)
                    operationOnPermission = null
                    return@addRequestPermissionsResultListener true
                }
            }
            return@addRequestPermissionsResultListener false
        }
        activity = binding.activity
        context = binding.activity.applicationContext
        /*return binding.activity*/
    }

    companion object {
        // Permissions and request constants 10172
        private const val REQUEST_COARSE_LOCATION_PERMISSIONS = 1451
        private const val REQUEST_ENABLE_BLUETOOTH = 1337
        private const val REQUEST_DISCOVERABLE_BLUETOOTH = 2137
    }
}
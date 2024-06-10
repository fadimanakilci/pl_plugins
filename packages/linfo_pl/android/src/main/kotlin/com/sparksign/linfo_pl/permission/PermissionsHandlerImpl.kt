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
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.sparksign.linfo_pl.permission.PermissionUtil.Companion.strictestStatus
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener


class PermissionsHandlerImpl(
    private val binding: ActivityPluginBinding,
    private val context: Context,
    private var activityResult: Result?
): RequestPermissionsResultListener, ActivityResultListener {
    private val activity: Activity = binding.activity
    private var requestCount: Int? = null
    private var pendingRequestCount: Int? = null
    private var requestResults: HashMap<Int, Int>? = null
    private var successCallback: RequestPermissionSuccessCallback? = null

    private val bluetoothPermissionHandlerImpl: BluetoothPermissionHandlerImpl =
        BluetoothPermissionHandlerImpl(binding, context, activityResult)

    val operationsOnPermission: MutableMap<Int, OperationOnPermission> = HashMap()
    private var lastEventId = 1452

//    fun checkPermissions(): Boolean? {
//        val permissions = ArrayList<String>()
//        var result: Boolean? = null
//
////    bluetoothPermissionHandlerImpl = BluetoothPermissionHandlerImpl(activityPluginBinding, activeContext)
//
//        bluetoothPermissionHandlerImpl.addResultListener()
//
//        if (Build.VERSION.SDK_INT >= 31) {
//            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
//        } else if (Build.VERSION.SDK_INT <= 30) {
//            permissions.add(Manifest.permission.BLUETOOTH)
//        }
//
////    bluetoothPermissionHandlerImpl!!.ensurePermissions(permissions, (granted: Boolean, perm: String?) {
////
////    })
//
//        ensurePermissions(permissions) { granted, perm ->
////            result = if (!granted) {
////                //                result.error(
////                //                    "turnOn",
////                //                    java.lang.String.format("FlutterBluePlus requires %s permission", perm), null
////                //                )
////                Log.e("PERMISSION", "FlutterBluePlus requires $perm permission")
////                false
////            } else {
////                true
////            }
//            if(!granted) {
//                Log.e("Permisson", "Requires $perm permission")
//            }
//
//            result = granted
//        }
//        Log.i("Permission", "Test Result: $result")
//
//        return result
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ): Boolean {
//        Log.i("Permission", "onRequestPermissionsResult Girdi")
//        val operation: OperationOnPermission = operationsOnPermission[requestCode]!!
//        return if (operation != null && grantResults.isNotEmpty()) {
//            operation.op(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0])
//            Log.i("Permission", "onRequestPermissionsResult true")
//            true
//        } else {
//            Log.i("Permission", "onRequestPermissionsResult false")
//            false
//        }
//    }

//    fun ensurePermissions(permissions: List<String>, operation: OperationOnPermission) {
//        // only request permission we don't already have
//        val permissionsNeeded: MutableList<String> = ArrayList()
//        for (permission in permissions) {
//            if (ContextCompat.checkSelfPermission(context!!, permission)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissionsNeeded.add(permission)
//            }
//        }
//
//        // no work to do?
//        if (permissionsNeeded.isEmpty()) {
//            operation.op(true, null)
//            return
//        }
//        askPermission(permissionsNeeded, operation)
//    }
//
//    private fun askPermission(permissionsNeeded: MutableList<String>?, operation: OperationOnPermission) {
//        // finished asking for permission? call callback
//        if (permissionsNeeded.isNullOrEmpty()) {
//            Log.i("Permission", "Test: Null Or Empty")
//            operation.op(true, null)
//            return
//        }
//        val nextPermission: String = permissionsNeeded.removeAt(0)
////        Log.i("LinfoPL", "Test: ${permissionsNeeded.size} - $nextPermission")
//
//        Log.i("Permission", "Test: Request ${operationsOnPermission.size}")
//
//        operationsOnPermission[lastEventId] = OperationOnPermission { granted, permission ->
//            Log.i("Permission", "Test: ILK")
//            operationsOnPermission.remove(lastEventId)
//            if (!granted) {
//                Log.i("Permission", "Test: IF")
//                operation.op(false, permission)
//                // return
//            }
//            Log.i("Permission", "Test: IF DISI")
//            // Recursively ask for the next permission
//            askPermission(permissionsNeeded, operation)
//        }
//        Log.i("Permission", "Test: Request ${operationsOnPermission.size}")
//
//        try {
//            ActivityCompat.requestPermissions(
//                binding.activity,
//                arrayOf(nextPermission),
////                permissionsNeeded.toTypedArray(),
//                lastEventId
//            )
////            bluetoothPermissionHandlerImpl.operationOnPermission = operation
//        } catch (e: Exception) {
//            Log.i("Permission", "Test: Error - ${e.message}")
//        } finally {
//            Log.i("Permission", "Test: $lastEventId")
//        }
//
//        lastEventId++
//        Log.i("Permission", "Test: $lastEventId")
//    }
//
//    fun askedPermissions(permissions: ArrayList<String>): Boolean? {
//        var result: Boolean? = null
//        ensurePermissions(permissions) { granted, perm ->
//            result = if (!granted) {
//                //                result.error(
//                //                    "turnOn",
//                //                    java.lang.String.format("FlutterBluePlus requires %s permission", perm), null
//                //                )
//                Log.e("PERMISSION", "FlutterBluePlus requires $perm permission")
//                false
//            } else {
//                true
//            }
//        }
//        Log.i("Permission", "Test Result Asked: $result")
//        return result
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        val status: Int
        val permission: Int

        if (requestCode == PermissionConstant.PERMISSION_CODE_IGNORE_BATTERY_OPTIMIZATIONS) {
            permission = PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS
            status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val packageName = context.packageName
                val pm: PowerManager =
                    context.getSystemService(Context.POWER_SERVICE) as PowerManager
                if (pm.isIgnoringBatteryOptimizations(packageName))
                    PermissionConstant.PERMISSION_STATUS_GRANTED
                else
                    PermissionConstant.PERMISSION_STATUS_DENIED
            } else {
                PermissionConstant.PERMISSION_STATUS_RESTRICTED
            }
        } else if (requestCode == PermissionConstant.PERMISSION_CODE_MANAGE_EXTERNAL_STORAGE) {
            status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager())
                    PermissionConstant.PERMISSION_STATUS_GRANTED
                else
                    PermissionConstant.PERMISSION_STATUS_DENIED
            } else {
                return false
            }
            permission = PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE
        } else if (requestCode == PermissionConstant.PERMISSION_CODE_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                status =
                    if (Settings.canDrawOverlays(activity))
                        PermissionConstant.PERMISSION_STATUS_GRANTED
                    else
                        PermissionConstant.PERMISSION_STATUS_DENIED
                permission = PermissionConstant.PERMISSION_GROUP_SYSTEM_ALERT_WINDOW
            } else {
                return false
            }
        } else if (requestCode == PermissionConstant.PERMISSION_CODE_REQUEST_INSTALL_PACKAGES) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                status =
                    if (activity.packageManager.canRequestPackageInstalls())
                        PermissionConstant.PERMISSION_STATUS_GRANTED
                    else
                        PermissionConstant.PERMISSION_STATUS_DENIED
                permission = PermissionConstant.PERMISSION_GROUP_REQUEST_INSTALL_PACKAGES
            } else {
                return false
            }
        } else if (requestCode == PermissionConstant.PERMISSION_CODE_ACCESS_NOTIFICATION_POLICY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val notificationManager =
                    activity.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
                status =
                    if (notificationManager.isNotificationPolicyAccessGranted)
                        PermissionConstant.PERMISSION_STATUS_GRANTED
                    else
                        PermissionConstant.PERMISSION_STATUS_DENIED
                permission = PermissionConstant.PERMISSION_GROUP_ACCESS_NOTIFICATION_POLICY
            } else {
                return false
            }
        } else if (requestCode == PermissionConstant.PERMISSION_CODE_SCHEDULE_EXACT_ALARM) {
            permission = PermissionConstant.PERMISSION_GROUP_SCHEDULE_EXACT_ALARM
            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms())
                    PermissionConstant.PERMISSION_STATUS_GRANTED
                else
                    PermissionConstant.PERMISSION_STATUS_DENIED
            } else {
                PermissionConstant.PERMISSION_STATUS_GRANTED
            }
        } else {
            return false
        }

        requestResults!![permission] = status
        pendingRequestCount = pendingRequestCount!! - 1

        // Post result if all requests have been handled.
        if (successCallback != null && pendingRequestCount == 0) {
            successCallback!!.onSuccess(requestResults!!)
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        if(requestCode != PermissionConstant.PERMISSION_CODE) {
            pendingRequestCount = 0
            return false
        }

        if(requestResults == null) {
            return false
        }

        if(permissions.isEmpty() && grantResults.isEmpty()) {
            Log.w(PermissionConstant.LOG_TAG, "onRequestPermissionsResult is called " +
                    "without results. This is probably caused by interfering request codes.")
            return false
        }

        for (i in permissions.indices) {
            val permissionName = permissions[i]

            val permission: Int =
                PermissionUtil.parseManifestName(permissionName)
            if (permission == PermissionConstant.PERMISSION_GROUP_UNKNOWN) continue
            val result = grantResults[i]
            if (permission == PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS) {
                val permissionStatus: Int =
                    PermissionUtil.toPermissionStatus(this.activity, permissionName, result)
                if (!requestResults!!.containsKey(PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS)) {
                    requestResults!![PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS] =
                        permissionStatus
                }
            } else if (permission == PermissionConstant.PERMISSION_GROUP_LOCATION) {
                val permissionStatus: Int =
                    PermissionUtil.toPermissionStatus(this.activity, permissionName, result)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (!requestResults!!.containsKey(PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS)) {
                        requestResults!![PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS] =
                            permissionStatus
                    }
                }
                if (!requestResults!!.containsKey(PermissionConstant.PERMISSION_GROUP_LOCATION_WHEN_IN_USE)) {
                    requestResults!![PermissionConstant.PERMISSION_GROUP_LOCATION_WHEN_IN_USE] =
                        permissionStatus
                }
                requestResults!![permission] = permissionStatus
            } else if (!requestResults!!.containsKey(permission)) {
                requestResults!![permission] =
                    PermissionUtil.toPermissionStatus(this.activity, permissionName, result)
            }
        }
        pendingRequestCount = pendingRequestCount?.minus(grantResults.size)

        // Post result if all requests have been handled.
        if (successCallback != null && pendingRequestCount == 0) {
            this.successCallback!!.onSuccess(requestResults!!);
        }
        return true;
    }

    fun checkPermissionStatus(
        permission: Int,
//        successCallback: CheckPermissionSuccessCallback
    ) : Int {
        return determinePermissionStatus(permission)
//        successCallback.onSuccess(determinePermissionStatus(permission))
    }

    fun requestPermissions(
        permissions: List<Int>,
//        successCallback: RequestPermissionSuccessCallback?,
//        errorCallback: ErrorCallback
    ) : HashMap<Int, Int>? {
        if (pendingRequestCount != null && pendingRequestCount!! > 0) {
//            errorCallback.onError(
//                "PermissionHandler.PermissionManager",
//                "A request for permissions is already running, please wait for it to finish before doing another request (note that you can request multiple permissions at the same time)."
//            )
            return null
        }
        if (activity == null) {
            Log.d(PermissionConstant.LOG_TAG, "Unable to detect current Activity.")
//            errorCallback.onError(
//                "PermissionHandler.PermissionManager",
//                "Unable to detect current Android Activity."
//            )
            return null
        }
        this.successCallback = successCallback
        requestResults = HashMap()
        pendingRequestCount = 0 // sanity check
        val permissionsToRequest = ArrayList<String>()
        for (permission in permissions) {
            val permissionStatus =
                determinePermissionStatus(permission)
            if (permissionStatus == PermissionConstant.PERMISSION_STATUS_GRANTED) {
                if (!requestResults!!.containsKey(permission)) {
                    requestResults!![permission] = PermissionConstant.PERMISSION_STATUS_GRANTED
                }
                continue
            }
            val names: List<String>? = PermissionUtil.getManifestNames(activity, permission)

            // check to see if we can find manifest names
            // if we can't add as unknown and continue
            if (names.isNullOrEmpty()) {
                if (!requestResults!!.containsKey(permission)) {
                    // On Android below M, the android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS flag in AndroidManifest.xml
                    // may be ignored and not visible to the App as it's a new permission setting as a whole.
                    if (permission == PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        requestResults!![permission] =
                            PermissionConstant.PERMISSION_STATUS_RESTRICTED
                    } else {
                        requestResults!![permission] = PermissionConstant.PERMISSION_STATUS_DENIED
                    }
                    // On Android below R, the android.permission.MANAGE_EXTERNAL_STORAGE flag in AndroidManifest.xml
                    // may be ignored and not visible to the App as it's a new permission setting as a whole.
                    if (permission == PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        requestResults!![permission] =
                            PermissionConstant.PERMISSION_STATUS_RESTRICTED
                    } else {
                        requestResults!![permission] = PermissionConstant.PERMISSION_STATUS_DENIED
                    }
                }
                continue
            }

            // Request special permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permission == PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS) {
                launchSpecialPermission(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    PermissionConstant.PERMISSION_CODE_IGNORE_BATTERY_OPTIMIZATIONS
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && permission == PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE) {
                launchSpecialPermission(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    PermissionConstant.PERMISSION_CODE_MANAGE_EXTERNAL_STORAGE
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permission == PermissionConstant.PERMISSION_GROUP_SYSTEM_ALERT_WINDOW) {
                launchSpecialPermission(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    PermissionConstant.PERMISSION_CODE_SYSTEM_ALERT_WINDOW
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && permission == PermissionConstant.PERMISSION_GROUP_REQUEST_INSTALL_PACKAGES) {
                launchSpecialPermission(
                    Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                    PermissionConstant.PERMISSION_CODE_REQUEST_INSTALL_PACKAGES
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permission == PermissionConstant.PERMISSION_GROUP_ACCESS_NOTIFICATION_POLICY) {
                launchSpecialPermission(
                    Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS,
                    PermissionConstant.PERMISSION_CODE_ACCESS_NOTIFICATION_POLICY
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && permission == PermissionConstant.PERMISSION_GROUP_SCHEDULE_EXACT_ALARM) {
                launchSpecialPermission(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    PermissionConstant.PERMISSION_CODE_SCHEDULE_EXACT_ALARM
                )
            } else {
                permissionsToRequest.addAll(names)
                pendingRequestCount = pendingRequestCount!! + names.size
            }
        }

        // Request runtime permissions.
        if (permissionsToRequest.size > 0) {
            val requestPermissions = permissionsToRequest.toTypedArray()
            ActivityCompat.requestPermissions(
                activity,
                requestPermissions,
                PermissionConstant.PERMISSION_CODE
            )
        }

        // Post results immediately if no requests are pending.
//        if (this.successCallback != null && pendingRequestCount == 0) {
//            this.successCallback!!.onSuccess(requestResults!!)
//        }
        return requestResults
    }

    private fun determinePermissionStatus(permission: Int): Int {
        if (permission == PermissionConstant.PERMISSION_GROUP_NOTIFICATION) {
            return checkNotificationPermissionStatus()
        }
        if (permission == PermissionConstant.PERMISSION_GROUP_BLUETOOTH) {
            return checkBluetoothPermissionStatus()
        }
        if (permission == PermissionConstant.PERMISSION_GROUP_BLUETOOTH_CONNECT || permission == PermissionConstant.PERMISSION_GROUP_BLUETOOTH_SCAN || permission == PermissionConstant.PERMISSION_GROUP_BLUETOOTH_ADVERTISE) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                return checkBluetoothPermissionStatus()
            }
        }

        val names: List<String>? = PermissionUtil.getManifestNames(context, permission)
        if (names == null) {
            Log.d(
                PermissionConstant.LOG_TAG,
                "No android specific permissions needed for: $permission"
            )
            return PermissionConstant.PERMISSION_STATUS_GRANTED
        }

        //if no permissions were found then there is an issue and permission is not set in Android manifest
        if (names.isEmpty()) {
            Log.d(
                PermissionConstant.LOG_TAG,
                "No permissions found in manifest for: $names$permission"
            )

            // On Android below M, the android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS flag in AndroidManifest.xml
            // may be ignored and not visible to the App as it's a new permission setting as a whole.
            if (permission == PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    return PermissionConstant.PERMISSION_STATUS_RESTRICTED
                }
            }

            // On Android below R, the android.permission.MANAGE_EXTERNAL_STORAGE flag in AndroidManifest.xml
            // may be ignored and not visible to the App as it's a new permission setting as a whole.
            if (permission == PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    return PermissionConstant.PERMISSION_STATUS_RESTRICTED
                }
            }
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
        }
        val requiresExplicitPermission =
            context.applicationInfo.targetSdkVersion >= Build.VERSION_CODES.M
        if (requiresExplicitPermission) {
            val permissionStatuses: MutableSet<Int> = HashSet()
            for (name in names) {
                if (permission == PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS) {
                    val packageName = context.packageName
                    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                    if (pm.isIgnoringBatteryOptimizations(packageName)) {
                        permissionStatuses.add(PermissionConstant.PERMISSION_STATUS_GRANTED)
                    } else {
                        permissionStatuses.add(PermissionConstant.PERMISSION_STATUS_DENIED)
                    }
                } else if (permission == PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        permissionStatuses.add(PermissionConstant.PERMISSION_STATUS_RESTRICTED)
                    }
                    val status: Int =
                        if (Environment.isExternalStorageManager()) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
                    permissionStatuses.add(status)
                } else if (permission == PermissionConstant.PERMISSION_GROUP_SYSTEM_ALERT_WINDOW) {
                    val status: Int =
                        if (Settings.canDrawOverlays(context)) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
                    permissionStatuses.add(status)
                } else if (permission == PermissionConstant.PERMISSION_GROUP_REQUEST_INSTALL_PACKAGES) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val status: Int =
                            if (context.packageManager.canRequestPackageInstalls()) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
                        permissionStatuses.add(status)
                    }
                } else if (permission == PermissionConstant.PERMISSION_GROUP_ACCESS_NOTIFICATION_POLICY) {
                    val notificationManager =
                        context.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
                    val status: Int =
                        if (notificationManager.isNotificationPolicyAccessGranted) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
                    permissionStatuses.add(status)
                } else if (permission == PermissionConstant.PERMISSION_GROUP_SCHEDULE_EXACT_ALARM) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val alarmManager =
                            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val status: Int =
                            if (alarmManager.canScheduleExactAlarms()) PermissionConstant.PERMISSION_STATUS_GRANTED else PermissionConstant.PERMISSION_STATUS_DENIED
                        permissionStatuses.add(status)
                    } else {
                        permissionStatuses.add(PermissionConstant.PERMISSION_STATUS_GRANTED)
                    }
                } else {
                    val permissionStatus = ContextCompat.checkSelfPermission(context, name)
                    if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                        permissionStatuses.add(
                            PermissionUtil.determineDeniedVariant(
                                activity,
                                name
                            )
                        )
                    }
                }
            }
            if (!permissionStatuses.isEmpty()) {
                return strictestStatus(permissionStatuses)
            }
        }
        return PermissionConstant.PERMISSION_STATUS_GRANTED
    }

    private fun launchSpecialPermission(permissionAction: String, requestCode: Int) {
        if (activity == null) {
            return
        }
        val intent = Intent(permissionAction)
        if (permissionAction != Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS) {
            val packageName = activity.packageName
            intent.data = Uri.parse("package:$packageName")
        }
        activity.startActivityForResult(intent, requestCode)
        pendingRequestCount = pendingRequestCount!! + 1
    }

    fun shouldShowRequestPermissionRationale(
        permission: Int,
        successCallback: ShouldShowRequestPermissionRationaleSuccessCallback,
        errorCallback: ErrorCallback
    ) {
        if (activity == null) {
            Log.d(PermissionConstant.LOG_TAG, "Unable to detect current Activity.")
            errorCallback.onError(
                "PermissionHandler.PermissionManager",
                "Unable to detect current Android Activity."
            )
            return
        }
        val names: List<String>? = PermissionUtil.getManifestNames(activity, permission)

        // if isn't an android specific group then go ahead and return false;
        if (names == null) {
            Log.d(
                PermissionConstant.LOG_TAG,
                "No android specific permissions needed for: $permission"
            )
            // false = 0, true = 1
            successCallback.onSuccess(0)
            return
        }
        if (names.isEmpty()) {
            Log.d(
                PermissionConstant.LOG_TAG,
                "No permissions found in manifest for: $permission no need to show request rationale"
            )
            successCallback.onSuccess(0)
            return
        }
        successCallback.onSuccess(
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                names[0]
            )) 1 else 0
        )
    }

    private fun checkNotificationPermissionStatus(): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            val manager = NotificationManagerCompat.from(context)
            val isGranted = manager.areNotificationsEnabled()
            return if (isGranted) {
                PermissionConstant.PERMISSION_STATUS_GRANTED
            } else PermissionConstant.PERMISSION_STATUS_DENIED
        }
        val status = context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
        return if (status == PackageManager.PERMISSION_GRANTED) {
            PermissionConstant.PERMISSION_STATUS_GRANTED
        } else PermissionUtil.determineDeniedVariant(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    private fun checkBluetoothPermissionStatus(): Int {
        val names: List<String>? = PermissionUtil.getManifestNames(
            context,
            PermissionConstant.PERMISSION_GROUP_BLUETOOTH
        )
        val missingInManifest = names.isNullOrEmpty()
        if (missingInManifest) {
            Log.d(PermissionConstant.LOG_TAG, "Bluetooth permission missing in manifest")
            return PermissionConstant.PERMISSION_STATUS_DENIED
        }
        return PermissionConstant.PERMISSION_STATUS_GRANTED
    }
}

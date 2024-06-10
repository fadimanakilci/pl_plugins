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
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.Arrays


class PermissionUtil {
    companion object {
        fun parseManifestName(permission: String?): Int {
            return when (permission) {
                Manifest.permission.ACCESS_BACKGROUND_LOCATION      -> PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION            -> PermissionConstant.PERMISSION_GROUP_LOCATION
                Manifest.permission.BODY_SENSORS                    -> PermissionConstant.PERMISSION_GROUP_SENSORS
                Manifest.permission.BODY_SENSORS_BACKGROUND         -> PermissionConstant.PERMISSION_GROUP_SENSORS_ALWAYS
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS                     -> PermissionConstant.PERMISSION_GROUP_SMS
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE          -> PermissionConstant.PERMISSION_GROUP_STORAGE
                Manifest.permission.ACCESS_MEDIA_LOCATION           -> PermissionConstant.PERMISSION_GROUP_ACCESS_MEDIA_LOCATION
                Manifest.permission.ACTIVITY_RECOGNITION            -> PermissionConstant.PERMISSION_GROUP_ACTIVITY_RECOGNITION
                Manifest.permission.MANAGE_EXTERNAL_STORAGE         -> PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE
                Manifest.permission.SYSTEM_ALERT_WINDOW             -> PermissionConstant.PERMISSION_GROUP_SYSTEM_ALERT_WINDOW
                Manifest.permission.REQUEST_INSTALL_PACKAGES        -> PermissionConstant.PERMISSION_GROUP_REQUEST_INSTALL_PACKAGES
                Manifest.permission.ACCESS_NOTIFICATION_POLICY      -> PermissionConstant.PERMISSION_GROUP_ACCESS_NOTIFICATION_POLICY
                Manifest.permission.BLUETOOTH_SCAN                  -> PermissionConstant.PERMISSION_GROUP_BLUETOOTH_SCAN
                Manifest.permission.BLUETOOTH_ADVERTISE             -> PermissionConstant.PERMISSION_GROUP_BLUETOOTH_ADVERTISE
                Manifest.permission.BLUETOOTH_CONNECT               -> PermissionConstant.PERMISSION_GROUP_BLUETOOTH_CONNECT
                Manifest.permission.POST_NOTIFICATIONS              -> PermissionConstant.PERMISSION_GROUP_NOTIFICATION
                Manifest.permission.NEARBY_WIFI_DEVICES             -> PermissionConstant.PERMISSION_GROUP_NEARBY_WIFI_DEVICES
                Manifest.permission.SCHEDULE_EXACT_ALARM            -> PermissionConstant.PERMISSION_GROUP_SCHEDULE_EXACT_ALARM
                else                                                -> PermissionConstant.PERMISSION_GROUP_UNKNOWN
            }
        }

        fun getManifestNames(
            context: Context?,
            permission: Int
        ): List<String>? {
            val permissionNames = ArrayList<String>()
            when (permission) {
                PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS,
                PermissionConstant.PERMISSION_GROUP_LOCATION_WHEN_IN_USE,
                PermissionConstant.PERMISSION_GROUP_LOCATION -> {
                    // Note that the LOCATION_ALWAYS will deliberately fallthrough to the LOCATION
                    // case on pre Android Q devices. The ACCESS_BACKGROUND_LOCATION permission was only
                    // introduced in Android Q, before it should be treated as the
                    // ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION.
                    if (permission == PermissionConstant.PERMISSION_GROUP_LOCATION_ALWAYS
                        && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if (hasPermissionInManifest(
                                context,
                                permissionNames,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            )
                        ) permissionNames.add(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    }
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    ) permissionNames.add(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) permissionNames.add(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }

                PermissionConstant.PERMISSION_GROUP_SENSORS -> if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.BODY_SENSORS
                        )
                    ) {
                        permissionNames.add(Manifest.permission.BODY_SENSORS)
                    }
                }

                PermissionConstant.PERMISSION_GROUP_SENSORS_ALWAYS -> if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.BODY_SENSORS_BACKGROUND
                        )
                    ) {
                        permissionNames.add(Manifest.permission.BODY_SENSORS_BACKGROUND)
                    }
                }

                PermissionConstant.PERMISSION_GROUP_SMS -> {
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.SEND_SMS
                        )
                    ) permissionNames.add(
                        Manifest.permission.SEND_SMS
                    )
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.RECEIVE_SMS
                        )
                    ) permissionNames.add(
                        Manifest.permission.RECEIVE_SMS
                    )
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.READ_SMS
                        )
                    ) permissionNames.add(
                        Manifest.permission.READ_SMS
                    )
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.RECEIVE_WAP_PUSH
                        )
                    ) permissionNames.add(
                        Manifest.permission.RECEIVE_WAP_PUSH
                    )
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.RECEIVE_MMS
                        )
                    ) permissionNames.add(
                        Manifest.permission.RECEIVE_MMS
                    )
                }

                PermissionConstant.PERMISSION_GROUP_STORAGE -> {
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) permissionNames.add(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                        || Build.VERSION.SDK_INT == Build.VERSION_CODES.Q
                        && Environment.isExternalStorageLegacy()) {
                        if (hasPermissionInManifest(
                                context,
                                permissionNames,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        ) permissionNames.add(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                }

                PermissionConstant.PERMISSION_GROUP_IGNORE_BATTERY_OPTIMIZATIONS -> if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasPermissionInManifest(
                        context,
                        permissionNames,
                        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    )
                ) permissionNames.add(
                    Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                )

                PermissionConstant.PERMISSION_GROUP_ACCESS_MEDIA_LOCATION -> {
                    // The ACCESS_MEDIA_LOCATION permission is introduced in Android Q, meaning we
                    // should not handle permissions on pre Android Q devices.
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.ACCESS_MEDIA_LOCATION
                        )
                    ) permissionNames.add(
                        Manifest.permission.ACCESS_MEDIA_LOCATION
                    )
                }

                PermissionConstant.PERMISSION_GROUP_ACTIVITY_RECOGNITION -> {
                    // The ACTIVITY_RECOGNITION permission is introduced in Android Q, meaning we
                    // should not handle permissions on pre Android Q devices.
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.ACTIVITY_RECOGNITION
                        )
                    ) permissionNames.add(
                        Manifest.permission.ACTIVITY_RECOGNITION
                    )
                }

                PermissionConstant.PERMISSION_GROUP_BLUETOOTH -> if (hasPermissionInManifest(
                        context,
                        permissionNames,
                        Manifest.permission.BLUETOOTH
                    )
                ) permissionNames.add(
                    Manifest.permission.BLUETOOTH
                )

                PermissionConstant.PERMISSION_GROUP_MANAGE_EXTERNAL_STORAGE ->
                    // The MANAGE_EXTERNAL_STORAGE permission is introduced in Android R,
                    // meaning we should not handle permissions on pre Android R devices.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        )
                    ) permissionNames.add(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    )

                PermissionConstant.PERMISSION_GROUP_SYSTEM_ALERT_WINDOW -> if (hasPermissionInManifest(
                        context,
                        permissionNames,
                        Manifest.permission.SYSTEM_ALERT_WINDOW
                    )
                ) permissionNames.add(
                    Manifest.permission.SYSTEM_ALERT_WINDOW
                )

                PermissionConstant.PERMISSION_GROUP_REQUEST_INSTALL_PACKAGES ->
                    // The REQUEST_INSTALL_PACKAGES permission is introduced in Android M,
                    // meaning we should not handle permissions on pre Android M devices.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.REQUEST_INSTALL_PACKAGES
                        )
                    ) permissionNames.add(
                        Manifest.permission.REQUEST_INSTALL_PACKAGES
                    )

                PermissionConstant.PERMISSION_GROUP_ACCESS_NOTIFICATION_POLICY ->
                    // The REQUEST_NOTIFICATION_POLICY permission is introduced in Android M,
                    // meaning we should not handle permissions on pre Android M devices.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.ACCESS_NOTIFICATION_POLICY
                        )
                    ) permissionNames.add(
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY
                    )

                PermissionConstant.PERMISSION_GROUP_BLUETOOTH_SCAN -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // The BLUETOOTH_SCAN permission is introduced in Android S, meaning we should
                        // not handle permissions on pre Android S devices.
                        val result: String? =
                            determineBluetoothPermission(context!!, Manifest.permission.BLUETOOTH_SCAN)
                        if (result != null) {
                            permissionNames.add(result)
                        }
                    }
                }

                PermissionConstant.PERMISSION_GROUP_BLUETOOTH_ADVERTISE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // The BLUETOOTH_ADVERTISE permission is introduced in Android S, meaning we
                        // should not handle permissions on pre Android S devices.
                        val result: String? = determineBluetoothPermission(
                            context!!,
                            Manifest.permission.BLUETOOTH_ADVERTISE
                        )
                        if (result != null) {
                            permissionNames.add(result)
                        }
                    }
                }

                PermissionConstant.PERMISSION_GROUP_BLUETOOTH_CONNECT -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // The BLUETOOTH_CONNECT permission is introduced in Android S, meaning we
                        // should not handle permissions on pre Android S devices.
                        val result: String? =
                            determineBluetoothPermission(context!!, Manifest.permission.BLUETOOTH_CONNECT)
                        if (result != null) {
                            permissionNames.add(result)
                        }
                    }
                }

                PermissionConstant.PERMISSION_GROUP_NOTIFICATION ->
                    // The POST_NOTIFICATIONS permission is introduced in Android TIRAMISU, meaning
                    // we should not handle permissions on pre Android TIRAMISU devices.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    ) permissionNames.add(
                        Manifest.permission.POST_NOTIFICATIONS
                    )

                PermissionConstant.PERMISSION_GROUP_NEARBY_WIFI_DEVICES ->
                    // The NEARBY_WIFI_DEVICES permission is introduced in Android TIRAMISU, meaning
                    // we should not handle permissions on pre Android TIRAMISU devices.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.NEARBY_WIFI_DEVICES
                        )
                    ) permissionNames.add(
                        Manifest.permission.NEARBY_WIFI_DEVICES
                    )

                PermissionConstant.PERMISSION_GROUP_SCHEDULE_EXACT_ALARM ->
                    // The SCHEDULE_EXACT_ALARM permission is introduced in Android S, before
                    // Android 31 it should alway return Granted
                    if (hasPermissionInManifest(
                            context,
                            permissionNames,
                            Manifest.permission.SCHEDULE_EXACT_ALARM
                        )
                    ) permissionNames.add(
                        Manifest.permission.SCHEDULE_EXACT_ALARM
                    )

                PermissionConstant.PERMISSION_GROUP_REMINDERS,
                PermissionConstant.PERMISSION_GROUP_UNKNOWN -> return null
            }
            return permissionNames
        }

        private fun hasPermissionInManifest(
            context: Context?,
            confirmedPermissions: ArrayList<String>?,
            permission: String
        ): Boolean {
            var confirmedPermissions: ArrayList<String>? = confirmedPermissions
            try {
                if (confirmedPermissions != null) {
                    for (r in confirmedPermissions) {
                        if (r == permission) {
                            return true
                        }
                    }
                }
                if (context == null) {
                    Log.d(
                        PermissionConstant.LOG_TAG,
                        "Unable to detect current Activity or App Context."
                    )
                    return false
                }
                val info: PackageInfo? = getPackageInfo(context)
                if (info == null) {
                    Log.d(
                        PermissionConstant.LOG_TAG,
                        "Unable to get Package info, will not be able to determine permissions" +
                                " to request."
                    )
                    return false
                }
                confirmedPermissions = ArrayList(Arrays.asList(*info.requestedPermissions))
                for (r in confirmedPermissions) {
                    if (r == permission) {
                        return true
                    }
                }
            } catch (ex: Exception) {
                Log.d(PermissionConstant.LOG_TAG, "Unable to check manifest for permission: ", ex)
            }
            return false
        }

        fun toPermissionStatus(
            activity: Activity?,
            permissionName: String?,
            grantResult: Int
        ): Int {
            return if (grantResult == PackageManager.PERMISSION_DENIED) {
                determineDeniedVariant(activity, permissionName)
            } else PermissionConstant.PERMISSION_STATUS_GRANTED
        }

        fun strictestStatus(statuses: Collection<Int?>): Int {
            if (statuses.contains(PermissionConstant.PERMISSION_STATUS_NEVER_ASK_AGAIN))
                return PermissionConstant.PERMISSION_STATUS_NEVER_ASK_AGAIN
            if (statuses.contains(PermissionConstant.PERMISSION_STATUS_RESTRICTED))
                return PermissionConstant.PERMISSION_STATUS_RESTRICTED
            if (statuses.contains(PermissionConstant.PERMISSION_STATUS_DENIED))
                return PermissionConstant.PERMISSION_STATUS_DENIED
            return if (statuses.contains(PermissionConstant.PERMISSION_STATUS_LIMITED))
                PermissionConstant.PERMISSION_STATUS_LIMITED
            else
                PermissionConstant.PERMISSION_STATUS_GRANTED
        }

        fun strictestStatus(
            statusA: Int?,
            statusB: Int?
        ): Int {
            val statuses: MutableCollection<Int?> = HashSet()
            statuses.add(statusA)
            statuses.add(statusB)
            return strictestStatus(statuses)
        }

        fun determineDeniedVariant(
            activity: Activity?,
            permissionName: String?
        ): Int {
            if (activity == null) {
                return PermissionConstant.PERMISSION_STATUS_DENIED
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return PermissionConstant.PERMISSION_STATUS_DENIED
            }
            val wasDeniedBefore: Boolean =
                wasPermissionDeniedBefore(activity, permissionName!!)
            val shouldShowRational: Boolean =
                !isNeverAskAgainSelected(activity, permissionName)
            val isDenied = if (wasDeniedBefore) !shouldShowRational else shouldShowRational
            if (!wasDeniedBefore && isDenied) {
                if (permissionName != null) {
                    setPermissionDenied(activity, permissionName)
                }
            }
            return if (wasDeniedBefore && isDenied) {
                PermissionConstant.PERMISSION_STATUS_NEVER_ASK_AGAIN
            } else PermissionConstant.PERMISSION_STATUS_DENIED
        }

        private fun determineBluetoothPermission(context: Context, permission: String): String? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && hasPermissionInManifest(
                    context,
                    null,
                    permission
                )
            ) {
                return permission
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (hasPermissionInManifest(
                        context,
                        null,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    return Manifest.permission.ACCESS_FINE_LOCATION
                } else if (hasPermissionInManifest(
                        context,
                        null,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    return Manifest.permission.ACCESS_COARSE_LOCATION
                }
                return null
            } else if (hasPermissionInManifest(
                    context,
                    null,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                return Manifest.permission.ACCESS_FINE_LOCATION
            }
            return null
        }

        // Suppress deprecation warnings since its purpose is to support to be backwards compatible with
        // pre TIRAMISU versions of Android
        @Throws(PackageManager.NameNotFoundException::class)
        private fun getPackageInfo(context: Context): PackageInfo? {
            val pm = context.packageManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
                )
            } else {
                pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            }
        }


        private const val SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY: String =
            "sp_permission_handler_permission_was_denied_before"

        private fun wasPermissionDeniedBefore(context: Context, permissionName: String): Boolean {
            val sharedPreferences = context.getSharedPreferences(permissionName, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(
                SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY,
                false
            )
        }

        private fun setPermissionDenied(context: Context, permissionName: String) {
            val sharedPreferences = context.getSharedPreferences(permissionName, Context.MODE_PRIVATE)
            sharedPreferences.edit()
                .putBoolean(SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY, true).apply()
        }

        fun isNeverAskAgainSelected(
            activity: Activity,
            name: String?
        ): Boolean {
            val shouldShowRequestPermissionRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    name!!
                )
            return !shouldShowRequestPermissionRationale
        }
    }
}
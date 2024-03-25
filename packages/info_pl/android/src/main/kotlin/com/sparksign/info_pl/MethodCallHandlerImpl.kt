/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.info_pl

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.FeatureInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.UUID

class MethodCallHandlerImpl(
    private val packageManager: PackageManager,
    private val windowManager: WindowManager,
    private val context: Context,
) : MethodChannel.MethodCallHandler {
    private val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @SuppressLint("HardwareIds", "LongLogTag")
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method.equals("getDeviceInfo")) {
            val build: MutableMap<String, Any?> = HashMap()

            build["deviceId"] = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            build["UUID"] = getUUID(context)
            build["model"] = Build.MODEL
            build["id"] = Build.ID
            build["manufacturer"] = Build.MANUFACTURER
            build["brand"] = Build.BRAND
            build["user"] = Build.USER
            build["type"] = Build.TYPE
            build["board"] = Build.BOARD
            build["host"] = Build.HOST
            build["fingerprint"] = Build.FINGERPRINT
            build["bootloader"] = Build.BOOTLOADER
            build["device"] = Build.DEVICE
            build["display"] = Build.DISPLAY
            build["hardware"] = Build.HARDWARE
            build["product"] = Build.PRODUCT
            build["tags"] = Build.TAGS
            build["isPhysicalDevice"] = !isEmulator
            build["simOperator"] = tm.simOperator
            build["simOperatorName"] = tm.simOperatorName
            build["simCountryIso"] = tm.simCountryIso
            build["networkOperator"] = tm.networkOperator
            build["networkOperatorName"] = tm.networkOperatorName
            build["networkCountryIso"] = tm.networkCountryIso
            build["mmsUserAgent"] = tm.mmsUserAgent
            build["mmsUAProfUrl"] = tm.mmsUAProfUrl
            build["myUid"] = android.os.Process.myUid()

            // 21
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                build["supported32BitAbis"] = listOf(*Build.SUPPORTED_32_BIT_ABIS)
                build["supported64BitAbis"] = listOf(*Build.SUPPORTED_64_BIT_ABIS)
                build["supportedAbis"] = listOf<String>(*Build.SUPPORTED_ABIS)
            } else {
                build["supported32BitAbis"] = emptyList<String>()
                build["supported64BitAbis"] = emptyList<String>()
                build["supportedAbis"] = emptyList<String>()
            }

            build["getElapsedCpuTime"] = android.os.Process.getElapsedCpuTime()
            // 24
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                build["getExclusiveCores"] = android.os.Process.getExclusiveCores()
                build["getStartUptimeMillis"] = android.os.Process.getStartUptimeMillis()
                build["getStartElapsedRealtime"] = android.os.Process.getStartElapsedRealtime()
            } else {
                build["getExclusiveCores"] = 0
                build["getStartUptimeMillis"] = 0
                build["getStartElapsedRealtime"] = 0
            }

            // 26
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                build["serialNumber"] = try {
                    Build.getSerial()
                } catch (ex: SecurityException) {
                    Build.UNKNOWN
                }
            } else {
                build["serialNumber"] = Build.SERIAL
            }

            // 28
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                build["networkSpecifier"] = tm.networkSpecifier
                build["isIsolated"] = android.os.Process.isIsolated()
            } else {
                build["networkSpecifier"] = "UNKNOWN"
                build["isIsolated"] = false
            }

            // 29
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                build["manufacturerCode"] = tm.manufacturerCode ?: "UNKNOWN"
                build["typeAllocationCode"] = tm.typeAllocationCode
                build["bluetoothUID"] = android.os.Process.BLUETOOTH_UID
            } else {
                build["manufacturerCode"] = "UNKNOWN"
                build["typeAllocationCode"] = "UNKNOWN"
                build["bluetoothUID"] = 0
            }

            // 30
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                build["wifiUID"] = android.os.Process.WIFI_UID
            } else {
                build["wifiUID"] = 0
            }

            // 33
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                build["myProcessName"] = android.os.Process.myProcessName()
                build["getStartRequestedUptimeMillis"] = android.os.Process.getStartRequestedUptimeMillis()
                build["getStartRequestedElapsedRealtime"] = android.os.Process.getStartRequestedElapsedRealtime()
            } else {
                build["myProcessName"] = "UNKNOWN"
                build["getStartRequestedUptimeMillis"] = 0
                build["getStartRequestedElapsedRealtime"] = 0
            }

            build["displayMetrics"] = getDisplayResult()
            build["version"] = getBuildVersion()
            build["systemFeatures"] = getSystemFeatures()

            result.success(build)
        } else {
            result.notImplemented()
        }
    }

    @SuppressLint("LongLogTag")
    private fun getBuildVersion(): MutableMap<String, Any> {
        val version: MutableMap<String, Any> = HashMap()

        version["codename"] = Build.VERSION.CODENAME
        version["incremental"] = Build.VERSION.INCREMENTAL
        version["release"] = Build.VERSION.RELEASE
        version["sdkInt"] = Build.VERSION.SDK_INT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            version["mediaPerformanceClass"] = Build.VERSION.MEDIA_PERFORMANCE_CLASS
        } else {
            version["mediaPerformanceClass"] = 0
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            version["releaseOrCodename"] = Build.VERSION.RELEASE_OR_CODENAME
        } else {
            version["releaseOrCodename"] = "UNKNOWN"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            version["releaseOrPreviewDisplay"] = Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY
        } else {
            version["releaseOrPreviewDisplay"] = "UNKNOWN"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            version["baseOS"] = Build.VERSION.BASE_OS
            version["previewSdkInt"] = Build.VERSION.PREVIEW_SDK_INT
            version["securityPatch"] = Build.VERSION.SECURITY_PATCH
        } else {
            version["baseOS"] = "UNKNOWN"
            version["previewSdkInt"] = 0
            version["securityPatch"] = "UNKNOWN"
        }

        return version
    }

    private fun getDisplayResult(): MutableMap<String, Any> {
        val display: Display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getRealMetrics(metrics)

        val displayResult: MutableMap<String, Any> = HashMap()
        displayResult["widthPx"] = metrics.widthPixels.toDouble()
        displayResult["heightPx"] = metrics.heightPixels.toDouble()
        displayResult["xDpi"] = metrics.xdpi
        displayResult["yDpi"] = metrics.ydpi

        return displayResult
    }

    private fun getSystemFeatures(): List<String> {
        val featureInfos: Array<FeatureInfo> = packageManager.systemAvailableFeatures
        return featureInfos
            .filterNot { featureInfo -> featureInfo.name == null }
            .map { featureInfo -> featureInfo.name }
    }

    @SuppressLint("HardwareIds", "SuspiciousIndentation", "LongLogTag")
    fun getUUID(context: Context): String {
        var buildId = Build.ID
        var buildBoard = Build.BOARD
        var deviceID = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val deviceUuid = UUID(
            deviceID.hashCode().toLong(),
            buildId.hashCode().toLong() shl 32 or
                    buildBoard.hashCode().toLong()
        )
        val uniqueId = deviceUuid.toString()
        if (BuildConfig.DEBUG) Log.d("getUUID", "uuid=$uniqueId")
        return uniqueId
    }

    private val isEmulator: Boolean
        get() = ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
}
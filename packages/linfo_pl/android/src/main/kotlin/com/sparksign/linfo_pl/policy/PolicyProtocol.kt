/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

package com.sparksign.linfo_pl.policy

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Base64
import android.util.Log
import io.flutter.plugin.common.PluginRegistry

// TODO: Gereksinimleri karşılayan cihaz ile devam et!!!

class PolicyProtocol(
    private val context             : Context,
    private val activity            : Activity,
    private val componentName       : ComponentName,
    private val devicePolicyManager : DevicePolicyManager,
) : PluginRegistry.ActivityResultListener {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        Log.i(PolicyUtil.LOG_TAG, "onActivityResult")
        when (requestCode) {
            PolicyUtil.REQUEST_ENABLE -> {
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(PolicyUtil.LOG_TAG, "Activity Result Ok")
                    devicePolicyManager.lockNow()
                }
            }
        }
        Log.i(PolicyUtil.LOG_TAG, "onActivityResult End")
        return false
    }

    fun turnOffScreen(password: String? = null): Boolean? {
        Log.i(PolicyUtil.LOG_TAG, "turnOffScreen")
        Log.i(PolicyUtil.LOG_TAG, "Kontrol component name: $componentName - ${context.packageName}")
        requestAdminPrivilegesIfNeeded()
        if(password.isNullOrEmpty() && devicePolicyManager.isAdminActive(componentName)) {
            Log.i(PolicyUtil.LOG_TAG, "If girdi")
            devicePolicyManager.lockNow()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.i(PolicyUtil.LOG_TAG,
                "${devicePolicyManager.isAffiliatedUser}\n" +
                        "${devicePolicyManager.isLogoutEnabled}\n" +
                        "${devicePolicyManager.activeAdmins}\n" +
//                        "${devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile}\n" +
                        "${devicePolicyManager.isDeviceIdAttestationSupported}\n"
//                        "${devicePolicyManager.isComplianceAcknowledgementRequired}\n" +
//                        "${devicePolicyManager.isPreferentialNetworkServiceEnabled}\n" +
//                        "${devicePolicyManager.isUniqueDeviceAttestationSupported}n" +
//                        "${devicePolicyManager.isUsbDataSignalingEnabled}\n" +
//                        "${devicePolicyManager.enrollmentSpecificId}\n" +
//                        "${devicePolicyManager.minimumRequiredWifiSecurityLevel}\n" +
//                        "${devicePolicyManager.preferentialNetworkServiceConfigs}\n"
                )
            }
            return true
        } else {
            Log.i(PolicyUtil.LOG_TAG, "Else")
            val passwordBytes = Base64.decode(password, Base64.NO_WRAP)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.i(
                    PolicyUtil.LOG_TAG,
                    "passwordComplexity: ${devicePolicyManager.passwordComplexity}\n" +
//                            "${devicePolicyManager.isActivePasswordSufficient}\n" +
//                            "${devicePolicyManager.isActivePasswordSufficientForDeviceRequirement}\n" +
                            "${devicePolicyManager.accountTypesWithManagementDisabled}\n" +
//                            "${devicePolicyManager.requiredPasswordComplexity}\n" +
                            "${devicePolicyManager.getPasswordMaximumLength(DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED)}\n"
                )
//                devicePolicyManager.resetPassword("0000", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY)
            }

            if(devicePolicyManager.isDeviceOwnerApp(componentName.packageName)) {
                devicePolicyManager.setPasswordQuality(
                    componentName,
                    DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i(PolicyUtil.LOG_TAG, "Else->If")
                    if (devicePolicyManager.isResetPasswordTokenActive(componentName)) {
                        Log.i(PolicyUtil.LOG_TAG, "Else->If->If")
                        devicePolicyManager.resetPasswordWithToken(
                            componentName, null, passwordBytes, 0
                        )
                        return true
                    } else {
                        Log.i(PolicyUtil.LOG_TAG, "Else->If->Else")
                        // Try to set again token
                        // On Android 8+, set reset password token if not active
                        return if (!devicePolicyManager.isResetPasswordTokenActive(componentName)) {
                            devicePolicyManager.setResetPasswordToken(
                                componentName, passwordBytes
                            )
                            Log.i(PolicyUtil.LOG_TAG, "Else->If->Else->If")
                            true
                        } else false
                    }
                } else {
                    Log.e(PolicyUtil.LOG_TAG, "Android Version < 26")
                    devicePolicyManager.lockNow()
                }
            } else {
                Log.e(PolicyUtil.LOG_TAG, "Device not owner app!")
                devicePolicyManager.lockNow()
            }
        }

//        if(!devicePolicyManager.isAdminActive(componentName)){
//            Log.i(PolicyUtil.LOG_TAG, "If girdi")
//            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
//            intent.putExtra(
//                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//                "You should enable the app!"
//            )
//            activity.startActivityForResult(intent, PolicyUtil.REQUEST_ENABLE)
//        } else {
//            Log.i(PolicyUtil.LOG_TAG, "Else girdi")
//            devicePolicyManager.lockNow()
//        }
//        Log.i(PolicyUtil.LOG_TAG, "turnOffScreen End")

        return false
    }

    // TODO: Şuan için null dönüyor. Araştır!
    private fun getApplicationRestrictions(): Map<String, String?>? {
        val packageName = context.packageName
        if (packageName != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val bundle = devicePolicyManager.getApplicationRestrictions(
                        componentName,
                        packageName
                    )
                    Log.i(PolicyUtil.LOG_TAG, "getApplicationRestrictions: ${PolicyUtil.bundleToMap(bundle)}")
                    return PolicyUtil.bundleToMap(bundle)
                }
                return null
            } catch (e: Exception) {
                return null
//                result.error("GET_APPLICATION_RESTRICTIONS", e.localizedMessage, null)
            }
        } else {
            return null
//            result.error(
//                "INVALID_ARGUMENTS",
//                "The 'packageName' argument is null or invalid",
//                null
//            )
        }
    }

    private fun requestAdminPrivilegesIfNeeded() {
        val isDeviceOwnerApp =
            devicePolicyManager.isDeviceOwnerApp(componentName.packageName)

        if (!isDeviceOwnerApp) {
            Log.e(PolicyUtil.LOG_TAG, "isDeviceOwnerApp = false")
        }

        if (!devicePolicyManager.isAdminActive(componentName)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.putExtra(
                    DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE,
                    context.packageName
                )
                intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                    context.packageName
                )
            } else {
                Log.e(PolicyUtil.LOG_TAG, "Version Code < 21")
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_SKIP_ENCRYPTION, true)
                intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                    componentName
                )
            } else {
                Log.e(PolicyUtil.LOG_TAG, "Version Code < 23")
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_SKIP_USER_CONSENT, true)
            } else {
                Log.e(PolicyUtil.LOG_TAG, "Version Code < 26")
            }

            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Administrator privileges are required for this app."
            )
//            activity.finish()
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivityForResult(intent, PolicyUtil.REQUEST_ENABLE)
        } else {
            Log.i(PolicyUtil.LOG_TAG, "Device admin privilege already granted.")
        }
    }
}

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
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.sparksign.linfo_pl.permission.PermissionsHandlerImpl
import com.sparksign.linfo_pl.utils.SendEventUtil
import io.flutter.plugin.common.EventChannel


class BluetoothBroadcastReceiver(
    private val context: Context,
    private val bluetoothProtocol: BluetoothProtocol,
    private val permissionsHandlerImpl: PermissionsHandlerImpl
) : BroadcastReceiver(), EventChannel.StreamHandler {
    private var events: EventChannel.EventSink? = null
    private lateinit var bluetoothStateReceiver: BroadcastReceiver

    override fun onReceive(p0: Context?, p1: Intent?) {

        Log.i("[LinfoPL]", "Gİrdiiiiiğğğğğğğğğ")
//        val action: String? = p1?.action
//
//        // no change?
//        if (action == null || BluetoothAdapter.ACTION_STATE_CHANGED != action) {
//            Log.i("[LinfoPL]", "ACTION_NO_STATE_CHANGED")
//            return
//        }
//
//        val adapterState: Int =
//            p1.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
//
//        Log.i("[LinfoPL]", "OnAdapterStateChanged: " + adapterStateString(adapterState))
//
//        // see: BmBluetoothAdapterState
//        val map = HashMap<String, Any>()
//        map["adapter_state"] = adapterStateEnum(adapterState)
//
////        invokeMethodUIThread("OnAdapterStateChanged", map)
//
//        // disconnect all devices
//        if (adapterState == BluetoothAdapter.STATE_TURNING_OFF ||
//            adapterState == BluetoothAdapter.STATE_OFF
//        ) {
//            Log.i("[LinfoPL]", "OnAdapterStateChanged: STATE_OFF")
////            disconnectAllDevices("adapterTurnOff")
//        }

        sendEvent()
//        events?.success(bluetoothProtocol.getState())
    }

    // TODO: Buraya tekrardan bak
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
//        while (true) {
            this.events = events
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sendEvent()
            } else {
                context.registerReceiver(this, IntentFilter(ACTION_STATE_CHANGED))
            }
//        }
        bluetoothStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
//                    val state = p1.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
//                    events?.success(state)
                    sendEvent()
                }
            }
        }

        context.registerReceiver(bluetoothStateReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }

    private fun sendEvent() {
        if (events != null) {
            val permissions = ArrayList<String>()

            if (Build.VERSION.SDK_INT >= 31) { // Android 12 (October 2021)
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            if (Build.VERSION.SDK_INT <= 30) { // Android 11 (September 2020)
                permissions.add(Manifest.permission.BLUETOOTH)
            }

            SendEventUtil().sendEvent(events!!, bluetoothProtocol.getState())

//            permissionsHandlerImpl.ensurePermissions(permissions) { granted, perm ->
//                if (granted === false) {
//
//                    return@ensurePermissions
//                }
//                SendEventUtil().sendEvent(events!!, bluetoothProtocol.getState())
//            }

        } else {
            Log.w("LinfoPL", "Null Events Warning");
        }
    }

    override fun onCancel(arguments: Any?) {
//        context.unregisterReceiver(bluetoothStateReceiver)
        try {
            context.unregisterReceiver(bluetoothStateReceiver)
//            context.unregisterReceiver(this)
        } catch (e: Exception) {
            throw java.lang.Exception(e)
        }
    }

    companion object {
        const val ACTION_STATE_CHANGED = "android.bluetooth.adapter.action.STATE_CHANGED"
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.power.wakelock

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.util.Log
import com.sparksign.linfo_pl.power.PowerBroadcastReceiver
import com.sparksign.linfo_pl.power.PowerProtocol
import com.sparksign.linfo_pl.power.PowerUtil
import com.sparksign.linfo_pl.window.WindowBroadcastReceiver
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import java.util.concurrent.Executors

// TODO: PC kaynaklı testleri ve çalışma prensibi kontrol edilemedi!!
//  Tekrardan bakılacak!!

class WakeLockStateHandlerImpl(
    private var context: Context,
//    private var powerManager: PowerManager?,
    private var powerProtocol: PowerProtocol,
): WakeLock, StreamHandler {
    private var events: EventSink? = null
    private lateinit var wakeLockBroadcastReceiver: WakeLockBroadcastReceiver
//    private val executor = Executors.newSingleThreadExecutor()

//    private val stateChangeListener = PowerManager.WakeLockStateListener {
//        Log.d(PowerUtil.LOG_TAG, "WakeLock state changed lis: $it")
//        sendEvent(it)
//    }

    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events
//        executor.execute {
//            Log.d(PowerUtil.LOG_TAG, "WakeLock state executor")
//            Thread.sleep(1000)
//        }
//        powerProtocol.wakeLock?.setStateListener(executor, stateChangeListener)
        sendEvent(null)
        wakeLockBroadcastReceiver = WakeLockBroadcastReceiver {
            sendEvent(it)
        }
        context.registerReceiver(wakeLockBroadcastReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        })
    }

    override fun onCancel(arguments: Any?) {
        events = null
        context.unregisterReceiver(wakeLockBroadcastReceiver)
//        powerProtocol.enableWakeLock()
    }

    override fun sendEvent(data: Intent?) {
        Log.d(PowerUtil.LOG_TAG, "WakeLock event: $data")
        events!!.success(powerProtocol.getWakeLockState())
    }

}
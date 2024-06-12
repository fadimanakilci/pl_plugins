/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

package com.sparksign.linfo_pl.window.orientation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.sparksign.linfo_pl.window.WindowProtocol
import com.sparksign.linfo_pl.window.WindowUtil
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.StreamHandler

class OrientationStateHandlerImpl(
    private var context: Context,
    private var windowProtocol: WindowProtocol,
) : Orientation, StreamHandler {
    private var events: EventChannel.EventSink? = null
    private lateinit var orientationBroadcastReceiver: OrientationBroadcastReceiver

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        this.events = events
        sendEvent(null)
        orientationBroadcastReceiver = OrientationBroadcastReceiver {
            sendEvent(it)
        }
        context.registerReceiver(orientationBroadcastReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_CONFIGURATION_CHANGED)
        })
    }

    override fun onCancel(arguments: Any?) {
        events = null
        context.unregisterReceiver(orientationBroadcastReceiver)
    }

    override fun sendEvent(data: Intent?) {
        Log.d(WindowUtil.LOG_TAG, "Orientation event: $data")
        events!!.success(windowProtocol.getOrientationState())
    }
}
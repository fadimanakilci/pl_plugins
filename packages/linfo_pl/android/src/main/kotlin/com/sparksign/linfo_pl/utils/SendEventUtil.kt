/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.utils

import android.R
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.plugin.common.EventChannel

class SendEventUtil {
    private val mainHandler: Handler = Handler(Looper.getMainLooper())

    fun sendEvent (
        events: EventChannel.EventSink,
        data: Any,
        interval: Long = IntervalUtil.normalIntervalMillis
    ) {
        Log.i("[LinfoPL]", "sendEvent: $data")
        val runnable = Runnable { events.success(data) }
        mainHandler.postDelayed(runnable, interval)

//        Handler(Looper.getMainLooper()).post {
//            if (methodChannel != null) {
//                methodChannel.invokeMethod(method, R.attr.data)
//            } else {
//                log(
//                    LogLevel.WARNING,
//                    "invokeMethodUIThread: tried to call method on closed channel: $method"
//                )
//            }
//        }
    }
}
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.window

import android.app.Activity
import android.util.Log

class KeepScreenOnTask(
    private val activity: Activity,
    private val callback: OnCompleteListener
) : Runnable {
    override fun run() {
        var result: Boolean? = null
        activity.runOnUiThread {
            synchronized(this) {
                if (!getScreenOn()) {
                    Log.d(WindowUtil.LOG_TAG, "Keep 1")
                    activity.window.addFlags(WindowUtil.FLAG_KEEP_SCREEN_ON)
                    Log.d(WindowUtil.LOG_TAG, "Keep 2")
                }
                result = getScreenOn()
            }
            callback.onComplete(result ?: false)
        }
    }

    private fun getScreenOn (): Boolean {
        Log.d(WindowUtil.LOG_TAG, "Screen On: ${
            activity.window.attributes.flags and
                    WindowUtil.FLAG_KEEP_SCREEN_ON != 0}")
        return activity.window.attributes.flags and
                WindowUtil.FLAG_KEEP_SCREEN_ON != 0
    }

}
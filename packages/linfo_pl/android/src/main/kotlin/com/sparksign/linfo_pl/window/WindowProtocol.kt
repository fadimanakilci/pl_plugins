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
import android.content.Context
import android.util.Log
import android.view.WindowManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// TODO: Thread ve Coroutine
// TODO: İncele ve iyi olan yöntem ile yapılandır
class WindowProtocol(
    private var context: Context,
    private var windowManager: WindowManager?,
    private var activity: Activity
) {
    fun getScreenOn (): Boolean {
        Log.d(WindowUtil.LOG_TAG, "Screen On: ${
            activity.window.attributes.flags and
                WindowUtil.FLAG_KEEP_SCREEN_ON != 0}")
        return activity.window.attributes.flags and
                WindowUtil.FLAG_KEEP_SCREEN_ON != 0
    }

    fun keepScreenOn(callback: OnCompleteListener) {
//    fun keepScreenOn() : Boolean {

//        return if (!getScreenOn()) {
//            activity.window.addFlags(WindowUtil.FLAG_KEEP_SCREEN_ON)
////            getScreenOn()
//            true
//        } else {
//            true
//        }

//        return activity.runOnUiThread {
//            synchronized(this) {
//                if (!getScreenOn()) {
//                    Log.d(WindowUtil.LOG_TAG, "Keep 1")
//                    activity.window.addFlags(WindowUtil.FLAG_KEEP_SCREEN_ON)
//                    Log.d(WindowUtil.LOG_TAG, "Keep 2")
//                }
//                getScreenOn()
//            }
//        }
        val task = KeepScreenOnTask(activity, callback)
//        var result: Boolean? = null
//        val task = KeepScreenOnTask(activity) {
//            result = it
//            Log.d(WindowUtil.LOG_TAG, "Keep ${result}")
//        }
        Thread(task).start()
//        val thread = Thread(task)
//        thread.start()
//        thread.join()
//        return  result ?: false
    }

    suspend fun discardScreenOn(): Boolean {
        // not running synchronized in thread
//        activity.runOnUiThread {
//            synchronized(this) {
//                if (getScreenOn()) {
//                    Log.d(WindowUtil.LOG_TAG, "Discard 1")
//                    activity.window.clearFlags(WindowUtil.FLAG_KEEP_SCREEN_ON)
//                    Thread.sleep(1)
//                    Log.d(WindowUtil.LOG_TAG, "Discard 2")
//                }
//            }
//        }
//        return getScreenOn()
        return withContext(Dispatchers.Main) {
            if (getScreenOn()) {
                Log.d(WindowUtil.LOG_TAG, "Discard 1")
                activity.window.clearFlags(WindowUtil.FLAG_KEEP_SCREEN_ON)
                Log.d(WindowUtil.LOG_TAG, "Discard 2")
            }
            getScreenOn()
        }
    }
}
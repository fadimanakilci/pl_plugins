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
import android.app.KeyguardManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


// TODO: Thread ve Coroutine
// TODO: İncele ve iyi olan yöntem ile yapılandır
class WindowProtocol(
    private var context: Context,
    private var windowManager: WindowManager?,
    private var keyguardManager: KeyguardManager?,
    private var activity: Activity
) {
    private var rotation: Int? = null
    private var orientation: Int? = null
    private var keyguardStatus: Int? = null

    fun getScreenOn (): Boolean {
        Log.d(WindowUtil.LOG_TAG, "Screen On: ${
            activity.window.attributes.flags and
                WindowUtil.FLAG_KEEP_SCREEN_ON != 0}")
        return activity.window.attributes.flags and
                WindowUtil.FLAG_KEEP_SCREEN_ON != 0
    }

    fun getOrientationState(): Int {
        orientation = activity.resources.configuration.orientation

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            rotation = activity.display!!.rotation
        } else {
            rotation = windowManager!!.getDefaultDisplay().rotation
        }

        Log.i(WindowUtil.LOG_TAG, "Orientation and Rotation: $orientation - $rotation")

        return when (orientation) {
//            Configuration.ORIENTATION_PORTRAIT ->
            WindowUtil.ORIENTATION_PORTRAIT ->
//                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                if (rotation == WindowUtil.ROTATION_0 || rotation == WindowUtil.ROTATION_90) {
                    WindowUtil.PORTRAIT_UP
                } else {
                    WindowUtil.PORTRAIT_DOWN
                }
            WindowUtil.ORIENTATION_LANDSCAPE ->
                if (rotation == WindowUtil.ROTATION_0 || rotation == WindowUtil.ROTATION_90) {
                    WindowUtil.LANDSCAPE_LEFT
                } else {
                    WindowUtil.LANDSCAPE_RIGHT
                }
            else -> WindowUtil.UNDEFINED
        }
    }

    fun changeBrightness(value: Float?): Boolean {
        val _value : Float = value ?: 1.0f
        val lp = activity.window.attributes
        lp.screenBrightness = _value
        MainScope().launch {
            activity.window.attributes = lp
        }
        return true
    }

    // Ekran Uyandırmasını Yapmıyor
//    fun schedulerWakeUp(
//        minutes: Double?,
//    ): Boolean {
//        val _minutes = minutes ?: 1.0
//
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
////            MainScope().launch {
//////                withContext(Dispatchers.IO) {
////                    Log.i(WindowUtil.LOG_TAG, "Girdi")
////                    Thread.sleep((_minutes * 30 * 1000L).toLong())
////                    Log.i(WindowUtil.LOG_TAG, "Bitti")
////                    val params: WindowManager.LayoutParams = activity.window.attributes
////                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
////                    params.screenBrightness = 0.05f
////                    activity.window.attributes = params
//////                }
////            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//                Log.d(WindowUtil.LOG_TAG, "onCreate: set window flags for API level > 27")
//                Thread.sleep((_minutes * 30 * 1000L).toLong())
//                activity.window.addFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//                            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                )
////                activity.setShowWhenLocked(true)
////                activity.setTurnScreenOn(true)
////                keyguardManager!!.newKeyguardLock("TEST").disableKeyguard()
//            } else {
//                Log.d(WindowUtil.LOG_TAG, "onCreate: onCreate:set window flags for API level < 27")
//                Thread.sleep((_minutes * 30 * 1000L).toLong())
//                activity.window.addFlags(
//                    (WindowManager.LayoutParams.FLAG_FULLSCREEN
//                            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                            or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                            or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
//                )
//            }
//            true
//        } else false
//    }

    fun requestKeyguard(callback: OnCompleteKeyguardDismiss) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            keyguardManager!!.requestDismissKeyguard(activity,
                KeyguardDismiss(callback))
        } else callback.onComplete(null)
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
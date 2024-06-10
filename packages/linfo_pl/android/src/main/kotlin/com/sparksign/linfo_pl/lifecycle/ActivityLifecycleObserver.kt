/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.plugin.common.MethodChannel

class ActivityLifecycleObserver(
    private var channel: MethodChannel,
): Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i("LIFECYCLE", "onActivityCreated")
        channel.invokeMethod("lifecycle", "created")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i("LIFECYCLE", "onActivityStarted")
        channel.invokeMethod("lifecycle", "started")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i("LIFECYCLE", "onActivityResumed")
        channel.invokeMethod("lifecycle", "resumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i("LIFECYCLE", "onActivityPaused")
        channel.invokeMethod("lifecycle", "paused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i("LIFECYCLE", "onActivityStopped")
        channel.invokeMethod("lifecycle", "stopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i("LIFECYCLE", "onActivitySaveInstanceState")
        channel.invokeMethod("lifecycle", "saveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i("LIFECYCLE", "onActivityDestroyed")
        channel.invokeMethod("lifecycle", "destroyed")
    }
}

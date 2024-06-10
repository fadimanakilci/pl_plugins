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

import android.bluetooth.BluetoothAdapter
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference

class ActivityLifecycleProtocol {
    fun getState(activityPluginBinding: ActivityPluginBinding): String {
        Log.i("Lifecycle", "TEST: ${(activityPluginBinding.lifecycle as HiddenLifecycleReference).lifecycle.currentState}")
        return convertStateString((activityPluginBinding.lifecycle as HiddenLifecycleReference).lifecycle.currentState)
    }

    fun observerEvent(activityPluginBinding: ActivityPluginBinding): String? {
        var _event: String? = null
        (activityPluginBinding.lifecycle as HiddenLifecycleReference)
            .lifecycle
            .addObserver(LifecycleEventObserver { source, event ->
                Log.i("Activity event: ", "$event - $source")
                _event = convertEventString(event)
            })
        return _event
    }

    companion object {
        @JvmStatic
        fun convertEventString(`as`: Lifecycle.Event): String {
            return when (`as`) {
                Lifecycle.Event.ON_ANY              -> "any"
                Lifecycle.Event.ON_CREATE           -> "created"
                Lifecycle.Event.ON_START            -> "started"
                Lifecycle.Event.ON_RESUME           -> "resumed"
                Lifecycle.Event.ON_PAUSE            -> "paused"
                Lifecycle.Event.ON_STOP             -> "stopped"
                Lifecycle.Event.ON_DESTROY          -> "destroyed"
                else                                -> "unknown"
            }
        }

        @JvmStatic
        private fun convertStateString(`as`: Lifecycle.State): String {
            return when (`as`) {
                Lifecycle.State.INITIALIZED         -> "initialized"
                Lifecycle.State.CREATED             -> "created"
                Lifecycle.State.STARTED             -> "started"
                Lifecycle.State.RESUMED             -> "resumed"
                Lifecycle.State.DESTROYED           -> "destroyed"
                else                                -> "unknown"
            }
        }
    }
}
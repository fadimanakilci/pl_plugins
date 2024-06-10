/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

/*
* PARTIAL_WAKE_LOCK:              Ensures that the CPU is running
* SCREEN_DIM_WAKE_LOCK:           Ensures that the screen is on (but may be dimmed)
* SCREEN_BRIGHT_WAKE_LOCK:        Ensures that the screen is on at full brightness
* FULL_WAKE_LOCK:                 Ensures that the screen and keyboard backlight are on at full brightness
* PROXIMITY_SCREEN_OFF_WAKE_LOCK: Turns the screen off when the proximity sensor activates.
*
* SCREEN_DIM_WAKE_LOCK, SCREEN_BRIGHT_WAKE_LOCK ve FULL_WAKE_LOCK
* yerine WindowManager kullanılması daha uygun
*/

package com.sparksign.linfo_pl.power

import android.os.PowerManager
import android.util.Log

// TODO: DOZE_WAKE_LOCK ve DRAW_WAKE_LOCK kullanımına bakılacak
class PowerUtil {
    companion object {
        const val LOG_TAG                                   : String     = "POWERMANAGER"
        const val WL_TAG                                    : String     = "LinfoPL::WakeLock"

        private const val THERMAL_STATUS_NONE               : Int        = 0
        private const val THERMAL_STATUS_LIGHT              : Int        = 1
        private const val THERMAL_STATUS_MODERATE           : Int        = 2
        private const val THERMAL_STATUS_SEVERE             : Int        = 3
        private const val THERMAL_STATUS_CRITICAL           : Int        = 4
        private const val THERMAL_STATUS_EMERGENCY          : Int        = 5
        private const val THERMAL_STATUS_SHUTDOWN           : Int        = 6

        private const val PARTIAL_WAKE_LOCK                 : Int        = 1
        private const val SCREEN_DIM_WAKE_LOCK              : Int        = 6
        private const val SCREEN_BRIGHT_WAKE_LOCK           : Int        = 10
        private const val FULL_WAKE_LOCK                    : Int        = 26
        private const val PROXIMITY_SCREEN_OFF_WAKE_LOCK    : Int        = 32

        fun getThermalStatus(getInt: Int?): String {
            return when (getInt) {
                THERMAL_STATUS_NONE                                     -> "none"
                THERMAL_STATUS_LIGHT                                    -> "light"
                THERMAL_STATUS_MODERATE                                 -> "moderate"
                THERMAL_STATUS_SEVERE                                   -> "severe"
                THERMAL_STATUS_CRITICAL                                 -> "critical"
                THERMAL_STATUS_EMERGENCY                                -> "emergency"
                THERMAL_STATUS_SHUTDOWN                                 -> "shutdown"
                else                                                    -> "unknown"
            }
        }

        fun setWakeLockState(state: String): Int? {
            return when (state) {
                "partial"                                               -> PARTIAL_WAKE_LOCK
                "dim"                                                   -> SCREEN_DIM_WAKE_LOCK
                "bright"                                                -> SCREEN_BRIGHT_WAKE_LOCK
                "full"                                                  -> FULL_WAKE_LOCK
                "proximity"                                             -> PROXIMITY_SCREEN_OFF_WAKE_LOCK
                else                                                    -> null
            }
        }
    }
}
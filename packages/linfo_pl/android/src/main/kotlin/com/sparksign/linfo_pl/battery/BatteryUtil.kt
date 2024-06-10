/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.battery

class BatteryUtil {
    companion object {
        private const val BATTERY_STATUS_UNKNOWN                = 1
        private const val BATTERY_STATUS_CHARGING               = 2
        private const val BATTERY_STATUS_DISCHARGING            = 3
        private const val BATTERY_STATUS_NOT_CHARGING           = 4
        private const val BATTERY_STATUS_FULL                   = 5

        private const val BATTERY_PLUGGED_AC                    = 1
        private const val BATTERY_PLUGGED_USB                   = 2
        private const val BATTERY_PLUGGED_WIRELESS              = 4
        private const val BATTERY_PLUGGED_DOCK                  = 8

        private const val BATTERY_HEALTH_UNKNOWN                = 1
        private const val BATTERY_HEALTH_GOOD                   = 2
        private const val BATTERY_HEALTH_OVERHEAT               = 3
        private const val BATTERY_HEALTH_DEAD                   = 4
        private const val BATTERY_HEALTH_OVER_VOLTAGE           = 5
        private const val BATTERY_HEALTH_UNSPECIFIED_FAILURE    = 6
        private const val BATTERY_HEALTH_COLD                   = 7


        fun getBatteryStatus(getInt: Int): String {
            return when (getInt) {
                BATTERY_STATUS_UNKNOWN                          -> "unknown"
                BATTERY_STATUS_CHARGING                         -> "charging"
                BATTERY_STATUS_DISCHARGING                      -> "discharging"
                BATTERY_STATUS_NOT_CHARGING                     -> "not_charging"
                BATTERY_STATUS_FULL                             -> "full"
                else                                            -> "unknown"
            }
        }

        fun getBatteryPlugged(getInt: Int): String {
            return when (getInt) {
                BATTERY_PLUGGED_AC                              -> "ac"
                BATTERY_PLUGGED_USB                             -> "usb"
                BATTERY_PLUGGED_WIRELESS                        -> "wireless"
                BATTERY_PLUGGED_DOCK                            -> "dock"
                else                                            -> "discharging"
            }
        }

        fun getBatteryHealth(getInt: Int): String {
            return when (getInt) {
                BATTERY_HEALTH_UNKNOWN                          -> "unknown"
                BATTERY_HEALTH_GOOD                             -> "good"
                BATTERY_HEALTH_OVERHEAT                         -> "overheat"
                BATTERY_HEALTH_DEAD                             -> "dead"
                BATTERY_HEALTH_OVER_VOLTAGE                     -> "over_voltage"
                BATTERY_HEALTH_UNSPECIFIED_FAILURE              -> "unspecified_failure"
                BATTERY_HEALTH_COLD                             -> "cold"
                else                                            -> "unknown"
            }
        }
    }
}
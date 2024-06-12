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

import com.sparksign.linfo_pl.power.PowerUtil

// WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
class WindowUtil {
    companion object {
        const val LOG_TAG                                   : String    = "WINDOWMANAGER"
        const val FLAG_KEEP_SCREEN_ON                       : Int       = 128

        const val ORIENTATION_UNDEFINED                     : Int       = 0
        const val ORIENTATION_PORTRAIT                      : Int       = 1
        const val ORIENTATION_LANDSCAPE                     : Int       = 2

        const val KEYGUARD_ERROR                            : Int       = 0
        const val KEYGUARD_SUCCEEDED                        : Int       = 1
        const val KEYGUARD_CANCELED                         : Int       = 2

        const val ROTATION_0                                : Int       = 0
        const val ROTATION_90                               : Int       = 1
        const val ROTATION_180                              : Int       = 2
        const val ROTATION_270                              : Int       = 3

        const val UNDEFINED                                 : Int       = 0
        const val LANDSCAPE_LEFT                            : Int       = 1
        const val PORTRAIT_UP                               : Int       = 2
        const val LANDSCAPE_RIGHT                           : Int       = 3
        const val PORTRAIT_DOWN                             : Int       = 4

        fun getPortraitState(getInt: Int?): String {
            return when (getInt) {
                LANDSCAPE_LEFT                              -> "left"
                PORTRAIT_UP                                 -> "up"
                LANDSCAPE_RIGHT                             -> "right"
                PORTRAIT_DOWN                               -> "down"
                else                                        -> "undefined"
            }
        }
    }
}
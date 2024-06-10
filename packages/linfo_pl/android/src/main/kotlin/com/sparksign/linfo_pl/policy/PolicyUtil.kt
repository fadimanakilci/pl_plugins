/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

package com.sparksign.linfo_pl.policy

import android.os.Bundle

// TODO: Request Kodları İçin Protocol Oluştur
class PolicyUtil {
    companion object {
        const val LOG_TAG                                   : String     = "DevicePolicyManager"
        const val REQUEST_ENABLE                            : Int        = 0


        fun bundleToMap(extras: Bundle): Map<String, String?> {
            val map: MutableMap<String, String?> = HashMap()
            val keySet = extras.keySet()
            val iterator: Iterator<String> = keySet.iterator()
            while (iterator.hasNext()) {
                val key = iterator.next()
                map[key] = extras.getString(key)
            }
            return map
        }
    }
}
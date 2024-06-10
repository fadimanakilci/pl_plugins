/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.power

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PowerBroadcastReceiver(
    private val onReceiveIntent: (Intent?) -> Unit,
) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        onReceiveIntent(p1)
    }
}
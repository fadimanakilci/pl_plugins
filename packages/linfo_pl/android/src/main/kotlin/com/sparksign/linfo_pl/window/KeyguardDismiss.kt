/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

package com.sparksign.linfo_pl.window

import android.app.KeyguardManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class KeyguardDismiss(
    private val callback: OnCompleteKeyguardDismiss,
) : KeyguardManager.KeyguardDismissCallback() {
    override fun onDismissError() {
        Log.d("Keyguard", "Error")
        callback.onComplete(WindowUtil.KEYGUARD_ERROR)
    }

    override fun onDismissSucceeded() {
        Log.d("Keyguard", "Success")
        callback.onComplete(WindowUtil.KEYGUARD_SUCCEEDED)
    }

    override fun onDismissCancelled() {
        Log.d("Keyguard", "Cancelled")
        callback.onComplete(WindowUtil.KEYGUARD_CANCELED)
    }
}
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

import android.content.Context
import android.os.Build
import android.os.PowerManager
import android.os.PowerManager.WakeLockStateListener
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

class PowerProtocol(
    private val context: Context,
    private val powerManager: PowerManager?,
) {
    var wakeLock: PowerManager.WakeLock? = null

    fun getPowerInteractive(): Boolean? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            powerManager?.isInteractive
        } else {
            null
        }
    }

    fun getPowerThermalStatus(): String {
        return PowerUtil.getThermalStatus(powerManager?.currentThermalStatus)
    }

    fun getWakeLockState(): Boolean? {
        Log.d(PowerUtil.LOG_TAG, "WakeLock state: ${wakeLock?.isHeld}")
        return if(wakeLock == null) null else wakeLock?.isHeld
    }

    fun enableWakeLock(): Boolean {
        if(wakeLock == null) {
            wakeLock = powerManager?.run {
                newWakeLock(
                    PowerManager.FULL_WAKE_LOCK,
                    PowerUtil.WL_TAG
                ).apply {
                    acquire()
//                Log.d(PowerUtil.LOG_TAG, "WakeLock state changed")
//                setStateListener(Executors.newSingleThreadExecutor()) {
//                    Log.d(PowerUtil.LOG_TAG, "WakeLock state changed: $it")
//                }
                }
            }
        } else if(wakeLock?.isHeld == false) {
            wakeLock?.acquire()
        } else {
            throw IllegalStateException("isHeld true")
        }

        return getWakeLockState()!!
    }

    // TODO: Throw Stack tuttuğu için iyi bir yöntem değil.
    //  Çağrı abi ile görüştükten sonra kendi exception yapını oluştur.
    //  Aynı zamanda loglamayı da çöz.
    fun disableWakeLock(): Boolean {
        if(wakeLock == null) {
            throw IllegalStateException("WakeLock is not active")
        } else if(wakeLock?.isHeld == true) {
            wakeLock?.release()
            return getWakeLockState()!!
        } else {
            throw IllegalStateException("isHeld false")
        }
    }

    // Coroutine Scope vs Virtual Thread vs Handler(Looper.getMainLooper()
    // Bunların avantaj dezavantaj ve ne için kullanıldıklarına bak!!
    // Coroutine -> asenkron
    // Looper -> looper döngü sağlar(messageQueue).
    // Handler -> her bir işi loopera iletir ve yürütülmeyi takip edip callback i sağlar.
    fun enableSchedulerWakeLock(
        minutes: Double?,
    ): Boolean {
        val _minutes = minutes ?: 1.0

        Log.d(PowerUtil.LOG_TAG, "WakeLock seconds: $_minutes")

        if(wakeLock == null) {
            wakeLock = powerManager?.run {
                newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    PowerUtil.WL_TAG
                ).apply {
                    acquire(10 * 60 * 1000L)
                    Thread.sleep((_minutes * 60 * 1000L).toLong())
                    release()
                }
            }
        } else if(wakeLock?.isHeld == false) {
            wakeLock?.acquire(10 * 60 * 1000L)
            Thread.sleep((_minutes * 60 * 1000L).toLong())
            wakeLock?.release()
        } else {
            throw IllegalStateException("isHeld true")
        }

        return getWakeLockState()!!
    }

    fun enableSchedulerWakeUp(
        minutes: Double?,
    ): Boolean {
        val _minutes = minutes ?: 1.0

        Log.d(PowerUtil.LOG_TAG, "WakeLock wake up seconds: $_minutes")

        if(wakeLock == null) {
            wakeLock = powerManager?.run {
                newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    PowerUtil.WL_TAG
                ).apply {
                    acquire(10 * 60 * 1000L)
                    Thread.sleep((_minutes * 60 * 1000L).toLong())
                    release()
                }
            }
        } else if(wakeLock?.isHeld == false) {
            wakeLock?.acquire(10 * 60 * 1000L)
            Thread.sleep((_minutes * 60 * 1000L).toLong())
            wakeLock?.release()
        } else {
            throw IllegalStateException("isHeld true")
        }

        return getWakeLockState()!!
    }

    // TODO: v2 de dönüş değeri olarak işlemin bitip bitmediğini
    //  kontrol et ki flutter tarafında buton inaktifliği yapabil.
    //  Aynı durum Schedular için de geçerli
    fun enablePeriodicWakeLock(
        repeat:         Int?,
        minutes:        Double?,
        waitMinutes:    Double?,
    ) : Boolean? {
        val _repeat         = repeat        ?: 2
        val _minutes        = minutes       ?: 1.0
        val _waitMinutes    = waitMinutes   ?: 0.5

        Log.d(
            PowerUtil.LOG_TAG,
            "WakeLock repeat: $_repeat - minutes: $_minutes - wait: $_waitMinutes"
        )

        if(wakeLock == null) {
            wakeLock = powerManager?.run {
                newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    PowerUtil.WL_TAG
                ).apply {
                    for (i in 0.._repeat) {
                        acquire(10 * 60 * 1000L /*10 minutes*/)
                        println("Is held tag 1: $isHeld")
                        Thread.sleep((_minutes * 60 * 1000L).toLong())
                        println("Is held tag 2: $isHeld")
                        release()

                        if (i != repeat) {
//                        Thread.sleep((0.08 * 60 * 1000L).toLong()) /// 5000
                            Thread.sleep((_waitMinutes * 60 * 1000L).toLong()) /// 5000
                            println("Wake lock tag if: true")
                        }
                        println("Is held tag 3: $isHeld")
                    }
                }
            }
        } else if(wakeLock?.isHeld == false) {
            for (i in 0.._repeat) {
                wakeLock?.acquire(10 * 60 * 1000L)
                println("Is held tag 1: ${wakeLock?.isHeld}")
                Thread.sleep((_minutes * 60 * 1000L).toLong())
                println("Is held tag 2: ${wakeLock?.isHeld}")
                wakeLock?.release()

                if (i != repeat) {
                    Thread.sleep((_waitMinutes * 60 * 1000L).toLong()) /// 5000
                    println("Wake lock tag if: true")
                }
                println("Is held tag 3: ${wakeLock?.isHeld}")
            }
        } else {
            throw IllegalStateException("isHeld true")
        }
        return null
    }

/// ________________________________________________________________________________________________

/// THERMAL LISTENER FARKLI DOSYADA
/// THERMAL STATUS YUKARIDA
/// AŞAĞIDAKİ KODLAR DENENDİ FAKAT İSTENİLEN ŞEKİLDE ÇALIŞMADI



//    private var powerThermalStatus: String? = null
//    private var thermalStatusFutureTask: FutureTask<Unit>? = null
//    private var thermalStatusFuture: Future<Any>? = null

//    val powerChangeListener =
//        PowerManager.OnThermalStatusChangedListener {
//            Log.i("POWERMANAGER", "Thermal Status $it")
//            powerThermalStatus = PowerUtil.getThermalStatus(it)
//        }

//    @RequiresApi(Build.VERSION_CODES.P)
//    private val executor: Executor = Executors.newFixedThreadPool(1)
//    private val executor: Executor = Executors.newSingleThreadExecutor()
//    private val executor: Executor = context.mainExecutor

//    val powerChangeListener =
//        PowerManager.OnThermalStatusChangedListener: (String?) -> Unit = { onReceive ->
//        Log.i("POWERMANAGER", "Thermal Status $it")
//        powerThermalStatus = PowerUtil.getThermalStatus(it)
//    }


//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun  addThermalListener() {
//        if (powerThermalStatus == null) {
////            powerManager?.addThermalStatusListener(powerChangeListener)
////            powerManager?.addThermalStatusListener(executor, powerChangeListener)
//            Log.i("POWERMANAGER", "Thermal Status 5 $powerThermalStatus")
////            thermalStatusFutureTask = FutureTask {
//                powerManager?.addThermalStatusListener(executor, powerChangeListener)
////            }
////            executor.execute(thermalStatusFutureTask)
//        }
//    }

//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun  addThermalListener(onReceive: (String?) -> Unit) {
//        if (powerThermalStatus == null) {
////            powerManager?.addThermalStatusListener(powerChangeListener)
//            powerManager?.addThermalStatusListener {
//                Log.i("POWERMANAGER", "Thermal Status $it")
//                powerThermalStatus = PowerUtil.getThermalStatus(it)
//                onReceive(powerThermalStatus)
//            }
//        }
//    }

//    fun getPowerThermalStatus(): String? {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
////            runBlocking {
////
////                Log.i("POWERMANAGER", "Thermal Status IN $powerThermalStatus")
////            }
////            delay(1000)
//
////            thermalStatusFutureTask = FutureTask {
////                addThermalListener()
////            }
//
////            thermalStatusFuture =
////                executor.execute(thermalStatusFutureTask)
////            executor.execute(thermalStatusFuture)
//
////            if((thermalStatusFutureTask != null && thermalStatusFutureTask!!.isDone)) {
////                return powerThermalStatus
////            }
////            return null
//        } else {
//            Log.w("POWERMANAGER", "SDK VERSION NOT Q")
//            return null
//        }
//    }

//    fun getPowerThermalStatus(onReceive: (String?) -> Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            addThermalListener(onReceive)
//        } else {
//            Log.w("POWERMANAGER", "SDK VERSION NOT Q")
//            null
//        }
//    }
}

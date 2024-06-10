/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.linfo_pl.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink

class NetworkBroadcastReceiver(
    private val context: Context,
    private val connectivityProtocol: NetworkProtocol
) : BroadcastReceiver(), EventChannel.StreamHandler {
    private var events: EventSink? = null
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        events?.success(connectivityProtocol.getNetworkTypes())
    }

    override fun onListen(arguments: Any?, events: EventSink) {
        this.events = events
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                // Belirli bir ağa bağlanıldığında çalışır
                override fun onAvailable(network: Network) {
                    Log.i("onListen", "onAvailable")
                    sendEvent()
                }

//                override fun onCapabilitiesChanged(
//                    network: Network, networkCapabilities: NetworkCapabilities
//                ) {
//                    Log.i("onListen", "onCapabilitiesChanged")
//                    sendEvent(1)
//                }

                // Bağlı oldunan ağ bağlantısı kaybedildiğinde çalışır
                override fun onLost(network: Network) {
                    // TODO: Network bağlantısı kapatıldığında buraya giriyor. Kontrol ve bildirimi yaz!!!
                    Log.i("onListen", "onLost")
                    sendEvent()
                }

                // Belirli bir ağa bağlanılamadığında çalışır
                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.i("onListen", "onUnavailable")
                    sendEvent()
                }
            }
            connectivityProtocol.getConnectivityManager()?.registerDefaultNetworkCallback(
                networkCallback!!
            )
        } else {
            context.registerReceiver(this, IntentFilter(CONNECTIVITY_ACTION))
        }
    }

    override fun onCancel(arguments: Any?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (networkCallback != null) {
                connectivityProtocol.getConnectivityManager()?.unregisterNetworkCallback(
                    networkCallback!!
                )
                networkCallback = null
            }
        } else {
            try {
                context.unregisterReceiver(this)
            } catch (e: Exception) {
                throw java.lang.Exception(e)
            }
        }
    }

    private fun sendEvent() {
        val runnable = Runnable { events!!.success(connectivityProtocol.getNetworkTypes()) }
        mainHandler.postDelayed(runnable, 50)
    }

    companion object {
        const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
        const val EXTRA_NETWORK = "android.net.extra.NETWORK"
        const val EXTRA_NO_CONNECTIVITY = "noConnectivity"
    }
}
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

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.ArrayMap
import android.util.Log
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

// TODO: Add Wifi Manager and Connectivity Manager all features
class NetworkProtocol(
    private val wifiManager: WifiManager,
    private val connectivityManager: ConnectivityManager? = null,
    private val telephonyManager: TelephonyManager? = null,
) {
    // Using deprecated `connectionInfo` call here to be able to get info on demand
    @Suppress("DEPRECATION")
    private val wifiInfo: WifiInfo
        get() = wifiManager.connectionInfo

    // Android returns "SSID"
    private fun getWifiName(): String? = wifiInfo.ssid

    private fun getWifiBSSID(): String? = wifiInfo.bssid

    private fun getWifiIPAddress(): String? {
        var ipAddress: String? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val linkAddresses = connectivityManager?.getLinkProperties(connectivityManager.activeNetwork)?.linkAddresses

            val ipV4Address = linkAddresses?.firstOrNull { linkAddress ->
                linkAddress.address.hostAddress?.contains('.')
                    ?: false
            }?.address?.hostAddress

            ipAddress = ipV4Address
        } else {
            @Suppress("DEPRECATION")
            val interfaceIp = wifiInfo!!.ipAddress
            if (interfaceIp != 0) ipAddress = formatIPAddress(interfaceIp)
        }
        return ipAddress
    }

    private fun getWifiSubnetMask(): String {
        val ip = getWifiIPAddress()
        var subnet = ""
        try {
            val inetAddress = InetAddress.getByName(ip)
            subnet = getIPv4Subnet(inetAddress)
        } catch (ignored: Exception) {
        }
        return subnet
    }

    private fun getBroadcastIP(): String? {
        var broadcastIP: String? = null
        val currentWifiIpAddress = getWifiIPAddress()
        val inetAddress = InetAddress.getByName(currentWifiIpAddress)
        try {
            val networkInterface = NetworkInterface.getByInetAddress(inetAddress)
            networkInterface.interfaceAddresses.forEach { interfaceAddress ->
                if (!interfaceAddress.address.isLoopbackAddress) {
                    if (interfaceAddress.broadcast != null) {
                        broadcastIP = interfaceAddress.broadcast.hostAddress
                    }
                }
            }
        } catch (ignored: Exception) {

        }
        return broadcastIP
    }

    private fun getIpV6(): String? {
        try {
            val ip = getWifiIPAddress()
            val ni = NetworkInterface.getByInetAddress(InetAddress.getByName(ip))
            for (interfaceAddress in ni.interfaceAddresses) {
                val address = interfaceAddress.address
                if (!address.isLoopbackAddress && address is Inet6Address) {
                    val ipaddress = address.getHostAddress()
                    if (ipaddress != null) {
                        return ipaddress.split("%").toTypedArray()[0]
                    }
                }
            }
        } catch (ignored: SocketException) {

        }
        return null
    }

    private fun getGatewayIPAddress(): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val linkAddresses = connectivityManager?.getLinkProperties(connectivityManager.activeNetwork)
            val dhcpServer = linkAddresses?.dhcpServerAddress?.hostAddress

            dhcpServer
        } else {
            @Suppress("DEPRECATION")
            val dhcpInfo = wifiManager.dhcpInfo
            val gatewayIPInt = dhcpInfo?.gateway

            gatewayIPInt?.let { formatIPAddress(it) }
        }
    }

    private fun formatIPAddress(intIP: Int): String =
        String.format(
            "%d.%d.%d.%d",
            intIP and 0xFF,
            intIP shr 8 and 0xFF,
            intIP shr 16 and 0xFF,
            intIP shr 24 and 0xFF
        )

    private fun getIPv4Subnet(inetAddress: InetAddress): String {
        try {
            val ni = NetworkInterface.getByInetAddress(inetAddress)
            val intAddresses = ni.interfaceAddresses
            for (ia in intAddresses) {
                if (!ia.address.isLoopbackAddress && ia.address is Inet4Address) {
                    val networkPrefix =
                        getIPv4SubnetFromNetPrefixLength(ia.networkPrefixLength.toInt())
                    if (networkPrefix != null) {
                        return networkPrefix.hostAddress!!
                    }
                }
            }
        } catch (ignored: Exception) {
        }
        return ""
    }

    private fun getIPv4SubnetFromNetPrefixLength(netPrefixLength: Int): InetAddress? {
        try {
            var shift = 1 shl 31
            for (i in netPrefixLength - 1 downTo 1) {
                shift = shift shr 1
            }
            val subnet = ((shift shr 24 and 255)
                .toString() + "."
                    + (shift shr 16 and 255)
                    + "."
                    + (shift shr 8 and 255)
                    + "."
                    + (shift and 255))
            return InetAddress.getByName(subnet)
        } catch (ignored: Exception) {
        }
        return null
    }

    private fun getNetworkTypesLegacy(): List<String> {
        // handle type for Android versions less than Android 6
        val info = connectivityManager!!.activeNetworkInfo
        val types: MutableList<String> = ArrayList()
        if (info == null || !info.isConnected) {
            types.add(NETWORK_NONE)
            return types
        }
        val type = info.type
        when (type) {
            ConnectivityManager.TYPE_BLUETOOTH -> types.add(NETWORK_BLUETOOTH)
            ConnectivityManager.TYPE_ETHERNET -> types.add(NETWORK_ETHERNET)
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_WIMAX -> types.add(
                NETWORK_WIFI
            )

            ConnectivityManager.TYPE_VPN -> types.add(NETWORK_VPN)
            ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_MOBILE_DUN, ConnectivityManager.TYPE_MOBILE_HIPRI -> types.add(
                NETWORK_MOBILE
            )

            else -> types.add(NETWORK_OTHER)
        }
        return types
    }

    fun getNetworkInfo(): MutableMap<String, String?> {
        val types: MutableMap<String, String?> = ArrayMap()

        getWifiName()?.let { types.put("wifiName", it)}
        getWifiBSSID()?.let { types.put("wifiBSSID", it) }
        getWifiIPAddress()?.let { types.put("wifiIP", it) }
        getIpV6()?.let { types.put("wifiIPv6", it) }
        getWifiSubnetMask().let { types.put("wifiSubmask", it) }
        getBroadcastIP()?.let { types.put("wifiBroadcast", it) }
        getGatewayIPAddress()?.let { types.put("wifiGateway", it) }

        return types
    }

    fun getNetworkTypes(): List<String> {
        val types: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager!!.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
//            val a = connectivityManager!!.allNetworkInfo
//            Log.i("networkProtocol", "Info: ${a.size} - ${a[0].typeName} - ${a[1].typeName} - ${a[2].typeName} - ${a[3].typeName}\nCapabilities: $capabilities")
            if (capabilities == null
                || !capabilities.hasCapability(NET_CAPABILITY_INTERNET)
            ) {
                types.add(NETWORK_NONE)
                return types
            }
            if (capabilities.hasTransport(TRANSPORT_WIFI)
                || capabilities.hasTransport(TRANSPORT_WIFI_AWARE)
            ) {
                types.add(NETWORK_WIFI)
            }
            if (capabilities.hasTransport(TRANSPORT_ETHERNET)) {
                types.add(NETWORK_ETHERNET)
            }
            if (capabilities.hasTransport(TRANSPORT_VPN)) {
                types.add(NETWORK_VPN)
            }
            if (capabilities.hasTransport(TRANSPORT_CELLULAR)) {
                types.add(NETWORK_MOBILE)
            }
            if (capabilities.hasTransport(TRANSPORT_BLUETOOTH)) {
                types.add(NETWORK_BLUETOOTH)
            }
            if (types.isEmpty()
                && capabilities.hasCapability(NET_CAPABILITY_INTERNET)
            ) {
                types.add(NETWORK_OTHER)
            }
            if (types.isEmpty()) {
                types.add(NETWORK_NONE)
            }
        } else {
            // For legacy versions, return a single type as before or adapt similarly if multiple types need to be supported
            return getNetworkTypesLegacy()
        }
        return types
    }

    fun getConnectivityManager(): ConnectivityManager? {
        return connectivityManager
    }

    fun stateOnRequest(): Boolean {
        val networks: List<String> = getNetworkTypes()
        for(network in networks) {
            if(network == NETWORK_WIFI
                || network == NETWORK_MOBILE
                || network == NETWORK_VPN) {
                Log.i("NETWORK", "State On Wifi but $network")
                return true
            } else {

            }
        }
        return false
    }

    fun stateOnWifi(): Boolean {
        val networks: List<String> = getNetworkTypes()
        for(network in networks) {
            return if(network == NETWORK_WIFI) {
                Log.i("NETWORK", "State On Wifi but $network")
                true
            } else {
                wifiManager.isWifiEnabled = true
                Log.i("NETWORK", "State On Wifi true")
                true
            }
        }
        return false
    }

    fun stateOnMobile(): Boolean? {
        // TODO: Add PermissionHandler android.permission.CHANGE_NETWORK_STATE
        //  and android.permission.WRITE_SETTINGS
        var result: Boolean? = null
        val networks: List<String> = getNetworkTypes()
        for(network in networks) {
            if(network == NETWORK_MOBILE) {
                Log.i("NETWORK", "State On Wifi but $network")
                return true
            } else {
                // API level 26 ve üzeri için
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    connectivityManager?.requestNetwork(
                        REQUEST_MOBILE,
                        object: ConnectivityManager.NetworkCallback() {
                            override fun onAvailable(network: Network) {
                                // Belirli bir ağa başarıyla bağlandığında yapılacak işlemler
                                result = true
                            }

                            override fun onLost(network: Network) {
                                // Bağlı olduğunuz ağ bağlantısını kaybettiğinizde yapılacak işlemler
                            }

                            override fun onUnavailable() {
                                // Belirli bir ağa bağlanılamadığında yapılacak işlemler
                                result = false
                            }
                    })
                } else {
                    Log.i("NETWORK", "State Unavailable Because Version < O")
                    result = false
                    // API level 25 ve altı için
//                    if (telephonyManager?.simState == TelephonyManager.SIM_STATE_READY) {
//                        connectivityManager.setMobileDataEnabled(true)
//                    }
                }
            }
        }
        return result
    }

//    fun stateOff(): Boolean {
//
//    }

    companion object {
        const val NETWORK_NONE                  = "none"
        const val NETWORK_WIFI                  = "wifi"
        const val NETWORK_MOBILE                = "mobile"
        const val NETWORK_ETHERNET              = "ethernet"
        const val NETWORK_BLUETOOTH             = "bluetooth"
        const val NETWORK_VPN                   = "vpn"
        const val NETWORK_OTHER                 = "other"

        const val TRANSPORT_CELLULAR            = 0
        const val TRANSPORT_WIFI                = 1
        const val TRANSPORT_BLUETOOTH           = 2
        const val TRANSPORT_ETHERNET            = 3
        const val TRANSPORT_VPN                 = 4
        const val TRANSPORT_WIFI_AWARE          = 5
        const val NET_CAPABILITY_INTERNET       = 12


        val REQUEST_MOBILE  = NetworkRequest.Builder()
                            .addTransportType(TRANSPORT_CELLULAR)
                            .build()
        val REQUEST         = NetworkRequest.Builder()
                            .addTransportType(TRANSPORT_WIFI)
                            .addTransportType(TRANSPORT_CELLULAR)
//                            .addTransportType(NET_CAPABILITY_INTERNET)
                            .build()
    }
}

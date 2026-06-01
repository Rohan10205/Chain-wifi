package com.chainnet.network.wifi

import android.net.ConnectivityManager
import android.net.Network
import java.net.Socket

class NetworkBinder(private val connectivityManager: ConnectivityManager) {
    fun bindProcessToNetwork(network: Network?) {
        connectivityManager.bindProcessToNetwork(network)
    }

    fun createBoundSocket(network: Network, host: String, port: Int): Socket {
        return network.socketFactory.createSocket(host, port)
    }
}

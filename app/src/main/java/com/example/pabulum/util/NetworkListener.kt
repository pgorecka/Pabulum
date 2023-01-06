package com.example.pabulum.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class NetworkListener: ConnectivityManager.NetworkCallback() {

    private val isInternetAvailable = MutableStateFlow(false)

    fun checkInternetAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isOnline = false

        connectivityManager.allNetworks.forEach { network->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isOnline = true
                    return@forEach
                }
            }
        }

        isInternetAvailable.value = isOnline

        return isInternetAvailable

    }

    override fun onAvailable(network: Network) {
        isInternetAvailable.value = true
    }

    override fun onLost(network: Network) {
        isInternetAvailable.value = false
    }
}
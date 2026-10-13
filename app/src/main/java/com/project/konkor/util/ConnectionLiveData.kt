package com.project.konkor.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.project.konkor.util.DoseNetworkHasInternet.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class ConnectionLiveData(context: Context) : LiveData<Boolean>() {


    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetwork() {
        postValue(validNetworks.size > 0)
    }


    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }


    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() =
        object : ConnectivityManager.NetworkCallback() {


//            override fun onAvailable(network: Network) {
//                Log.d(TAG, "onAvailable: $network")
//                val networkCapabilities = cm.getNetworkCapabilities(network)
//                val isInternet = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
//                Log.d(TAG, "onAvailable: $network , $isInternet")
//                if (isInternet == true) {
//                    validNetworks.add(network)
//                }
//                checkValidNetwork()
//            }

            override fun onAvailable(network: Network) {
                Log.d(TAG, "onAvailable: $network")
                val networkCapabilities = cm.getNetworkCapabilities(network)
                val hasInternetCapability =
                    networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
                Log.d(TAG, "onAvailable: $network , $hasInternetCapability")
                if (hasInternetCapability == true) {
                    CoroutineScope(IO).launch {
                        val hasInternet = DoseNetworkHasInternet.execute(network.socketFactory)
                        if (hasInternet) {
                            withContext(Main) {
                                Log.d(TAG, "onAvailable: adding network. $network")
                                validNetworks.add(network)
                                checkValidNetwork()
                            }
                        } else {
                            checkValidNetwork()
                        }
                    }
                }
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "onLost: $network")
                validNetworks.remove(network)
                checkValidNetwork()
            }
        }

}

object DoseNetworkHasInternet {

    const val TAG = "C-Manager"

    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            Log.d(TAG, "execute: PINGING google")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "execute: PING success.")
            true
        } catch (e: IOException) {
            Log.e(TAG, "execute: No Internet Connection", e)
            false
        }
    }
}
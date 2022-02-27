package com.goldmansanch.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
class NetworkUtil(private val context: Context)
{



    private val TAG = "NetworkUtil"

    /**
     * Checks whether the device has Internet access or not.
     * @return true if net is available, false otherwise
     */
    fun isConnectedToInternet(): Boolean
    {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networks = cm.allNetworks
        var hasInternet = false
        if (networks.isNotEmpty())
        {
            for (network in networks)
            {
                try
                {
                    val nc = cm.getNetworkCapabilities(network)
                    if (nc?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
                    {
                        hasInternet = true
                    }
                } catch (ex: SecurityException)
                {
                    Log.e(TAG, "Got security exception:$ex")
                }
            }
        }
        return hasInternet
    }
}
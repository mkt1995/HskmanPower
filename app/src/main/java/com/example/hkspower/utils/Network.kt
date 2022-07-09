package com.example.hkspower.utils

import android.content.Context
import android.net.ConnectivityManager

class Network {
    companion object{
     fun getConnectivityStatus(context: Context?): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = cm.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    }

}
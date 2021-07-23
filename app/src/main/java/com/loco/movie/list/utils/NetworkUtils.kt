package com.loco.movie.list.utils

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import timber.log.Timber

object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            netInfo != null && netInfo.isAvailable && netInfo.isConnected
        } catch (e: Exception) {
            Timber.e(e, "unable to check if network is available")
            false
        }
    }


}
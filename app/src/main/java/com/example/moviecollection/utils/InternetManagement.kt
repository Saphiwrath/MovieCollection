package com.example.moviecollection.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isOnline(
    ctx: Context
): Boolean {
    val connectivityManager = ctx
        .applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork )
    return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR ) == true ||
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI ) == true
}

fun openWirelessSettings(
    ctx: Context
) {
    val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS ).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    if (intent.resolveActivity(ctx.packageManager ) != null) {
        ctx.startActivity(intent)
    }
}
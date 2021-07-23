package com.loco.movie.list.utils

import android.content.Context
import android.widget.Toast

fun Context.isNetworkAvailable(): Boolean = NetworkUtils.isNetworkAvailable(this)

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, msg, duration).show()
}
package com.loco.movie.list.utils

import android.view.View

inline fun View.remove() {
    if (visibility != View.GONE) visibility = View.GONE
}

inline fun View.show() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}



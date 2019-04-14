package com.amqo.randomuser.ui.base

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceProvider(
    private val context: Context
) {

    fun getColor(colorId : Int): Int {
        return ContextCompat.getColor(context, colorId)
    }
}
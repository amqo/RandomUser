package com.amqo.randomuser.ui.base

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class ResourcesProvider(
    private val context: Context
) {

    fun formatDateColor(
        color: Int, prefix: String = "", date: String
    ): Spannable {
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val formattedDate = simpleDateFormat.format(originalDateFormat.parse(date))

        val spannable = SpannableStringBuilder(prefix)
        spannable.append(formattedDate)
        spannable.setSpan(
            ForegroundColorSpan(getColor(color)),
            prefix.length, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun formatStringColorUnderline(
        color: Int, text: String
    ): Spannable {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(
            ForegroundColorSpan(getColor(color)),
            0, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), 0, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    // Private functions

    private fun getColor(colorId : Int): Int = ContextCompat.getColor(context, colorId)
}
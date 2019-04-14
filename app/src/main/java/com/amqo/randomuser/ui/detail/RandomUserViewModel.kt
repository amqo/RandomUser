package com.amqo.randomuser.ui.detail

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.lifecycle.ViewModel
import com.amqo.randomuser.R
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.lazyDeferred
import com.amqo.randomuser.ui.base.ResourceProvider
import java.text.SimpleDateFormat
import java.util.*

class RandomUserViewModel(
    private val userId: String,
    private val randomUsersRepository: RandomUsersRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val randomUser by lazyDeferred {
        randomUsersRepository.getRandomUserWithId(userId)
    }

    fun getRegisteredMessage(
        randomUser: RandomUserEntry
    ): Spannable {
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val formattedDate = simpleDateFormat.format(originalDateFormat.parse(randomUser.registered.date))

        val messagePrefix = "Registered since "
        val spannable = SpannableStringBuilder(messagePrefix)
        spannable.append(formattedDate)
        spannable.setSpan(
            ForegroundColorSpan(resourceProvider.getColor(R.color.colorPrimary)),
            messagePrefix.length, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun getMailFormatted(
        randomUser: RandomUserEntry
    ): Spannable {
        val spannable = SpannableStringBuilder(randomUser.email)
        spannable.setSpan(
            ForegroundColorSpan(resourceProvider.getColor(android.R.color.holo_blue_dark)),
            0, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), 0, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun getMapUrl(
        randomUser: RandomUserEntry
    ): String {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" +
                "${randomUser.location.coordinates.latitude},${randomUser.location.coordinates.longitude}" +
                "&zoom=14&size=800x400&key=AIzaSyDdAVZuR18LjK1edIDx5rwvhP4Oh3FbraE"
    }
}
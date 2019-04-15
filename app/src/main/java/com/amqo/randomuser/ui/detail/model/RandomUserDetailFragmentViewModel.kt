package com.amqo.randomuser.ui.detail.model

import android.text.Spannable
import androidx.lifecycle.ViewModel
import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.data.network.ApiSecretKey
import com.amqo.randomuser.internal.lazyDeferred
import com.amqo.randomuser.ui.base.ResourcesProvider

class RandomUserDetailFragmentViewModel(
    private val userId: String,
    private val getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    val randomUser by lazyDeferred {
        getRandomUserWithIdUseCase.execute(userId)
    }

    fun getRegisteredMessage(
        randomUser: RandomUserEntry
    ): Spannable = resourcesProvider.formatDateColor(
            R.color.colorPrimary,"Registered since ", randomUser.registered.date)

    fun getMailFormatted(
        randomUser: RandomUserEntry
    ): Spannable = resourcesProvider.formatStringColorUnderline(android.R.color.holo_blue_dark, randomUser.email)

    fun getMapUrl(
        randomUser: RandomUserEntry
    ): String {
        if (ApiSecretKey.GOOGLE_MAPS_API_KEY.isEmpty()) {
            return ""
        }
        return "$GOOGLE_STATIC_MAP_BASE_URL?center=" +
                "${randomUser.location.coordinates.latitude},${randomUser.location.coordinates.longitude}" +
                "&zoom=$STATIC_MAP_ZOOM&size=$STATIC_MAP_SIZE&key=${ApiSecretKey.GOOGLE_MAPS_API_KEY}"

    }

    companion object {

        const val GOOGLE_STATIC_MAP_BASE_URL = "https://maps.googleapis.com/maps/api/staticmap"
        const val STATIC_MAP_ZOOM = 14
        const val STATIC_MAP_SIZE = "800x400"
    }
}
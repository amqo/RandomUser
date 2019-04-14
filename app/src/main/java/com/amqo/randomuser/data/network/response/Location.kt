package com.amqo.randomuser.data.network.response

import androidx.room.Embedded

data class Location(
    val city: String,
    val state: String,
    val street: String,
    @Embedded(prefix = "_coordinates")
    val coordinates: Coordinates
)
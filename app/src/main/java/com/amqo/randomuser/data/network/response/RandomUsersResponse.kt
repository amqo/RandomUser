package com.amqo.randomuser.data.network.response

import com.amqo.randomuser.data.db.entity.RandomUserEntry

data class RandomUsersResponse(
    val info: Info,
    val results: List<RandomUserEntry>
)
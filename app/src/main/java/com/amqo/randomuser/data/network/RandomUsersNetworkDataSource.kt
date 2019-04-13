package com.amqo.randomuser.data.network

import androidx.lifecycle.LiveData
import com.amqo.randomuser.data.network.response.RandomUsersResponse

interface RandomUsersNetworkDataSource {

    val downloadedRandomUsers: LiveData<RandomUsersResponse>

    suspend fun fetchRandomUsers(number: Int)
}
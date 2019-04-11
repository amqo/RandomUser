package com.amqo.randomuser.data.repository

import androidx.lifecycle.LiveData
import com.amqo.randomuser.db.entity.RandomUserEntry

interface RandomUsersRepository {

    suspend fun getRandomUsers(
        number: Int
    ): LiveData<out List<RandomUserEntry>>

    suspend fun getRandomUserWithId(
        id: String
    ): LiveData<out RandomUserEntry>
}
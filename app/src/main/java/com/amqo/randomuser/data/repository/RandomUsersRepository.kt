package com.amqo.randomuser.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry

interface RandomUsersRepository {

    companion object {

        const val PAGES_RANDOM_USERS_SIZE: Int = 40
    }

    suspend fun getNewRandomUsers()

    fun getRandomUsers(): DataSource.Factory<Int, RandomUserEntry>

    fun recoverRandomUser(
        randomUser: RandomUserEntry
    )

    fun getRandomUserWithId(
        id: String
    ): LiveData<out RandomUserEntry>

    fun deleteRandomUserWithId(id: String)

    fun searchRandomUsers(
        search: String
    ): DataSource.Factory<Int, RandomUserEntry>
}
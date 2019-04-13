package com.amqo.randomuser.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.amqo.randomuser.db.entity.RandomUserEntry

interface RandomUsersRepository {

    companion object {

        const val PAGES_RANDOM_USERS_SIZE: Int = 40
    }

    suspend fun getNewRandomUsers()

    fun getRandomUsers(): DataSource.Factory<Int, RandomUserEntry>

    fun getRandomUserWithId(
        id: String
    ): LiveData<out RandomUserEntry>

    suspend fun deleteRandomUserWithId(id: String)

    fun filterUsersWithSearch(
        search: String
    ): DataSource.Factory<Int, RandomUserEntry>
}
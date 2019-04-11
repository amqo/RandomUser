package com.amqo.randomuser.data.repository

import androidx.lifecycle.LiveData
import com.amqo.randomuser.data.network.RandomUsersNetworkDataSource
import com.amqo.randomuser.data.network.response.RandomUsersResponse
import com.amqo.randomuser.db.RandomUsersDao
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.observeForever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RandomUsersRepositoryImpl(
    private val randomUsersDao: RandomUsersDao,
    private val randomUsersNetworkDataSource: RandomUsersNetworkDataSource
) : RandomUsersRepository {

    init {
        randomUsersNetworkDataSource.apply {
            observeForever(downloadedRandomUsers) { newRandomUsers ->
                persistFetchedRandomUsers(newRandomUsers)
            }
        }
    }

    override suspend fun getRandomUsers(number: Int): LiveData<out List<RandomUserEntry>> {
        randomUsersNetworkDataSource.fetchRandomUsers(number)
        return randomUsersDao.getRandomUsers(number)
    }

    override suspend fun getRandomUserWithId(id: String): LiveData<out RandomUserEntry> {
        return randomUsersDao.getRandomUserWithId(id)
    }

    private fun persistFetchedRandomUsers(fetchedRandomUsers: RandomUsersResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            randomUsersDao.deleteAll()
            randomUsersDao.insert(fetchedRandomUsers.results)
        }
    }
}
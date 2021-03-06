package com.amqo.randomuser.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.amqo.randomuser.data.db.RandomUsersDao
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.network.RandomUsersNetworkDataSource
import com.amqo.randomuser.data.network.response.RandomUsersResponse
import com.amqo.randomuser.data.repository.RandomUsersRepository.Companion.PAGES_RANDOM_USERS_SIZE
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

    override suspend fun getNewRandomUsers() {
        randomUsersNetworkDataSource.fetchRandomUsers(PAGES_RANDOM_USERS_SIZE)
    }

    override fun getRandomUsers(): DataSource.Factory<Int, RandomUserEntry> {
        return randomUsersDao.getAllPaged()
    }

    override fun recoverRandomUser(randomUser: RandomUserEntry) {
        randomUsersDao.recover(randomUser.getId())
    }

    override fun getRandomUserWithId(
        id: String
    ): LiveData<out RandomUserEntry> {
        return randomUsersDao.getWithId(id)
    }

    override fun deleteRandomUserWithId(id: String) {
        randomUsersDao.deleteWithId(id)
    }

    override fun searchRandomUsers(
        search: String
    ): DataSource.Factory<Int, RandomUserEntry> {
        return randomUsersDao.getWithSearch(search)
    }

    // Private functions

    private fun persistFetchedRandomUsers(fetchedRandomUsers: RandomUsersResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            randomUsersDao.insert(fetchedRandomUsers.results)
        }
    }
}
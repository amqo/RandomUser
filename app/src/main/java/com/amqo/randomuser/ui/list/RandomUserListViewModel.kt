package com.amqo.randomuser.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.data.repository.RandomUsersRepository.Companion.PAGES_RANDOM_USERS_SIZE
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.lazyDeferred

class RandomUserListViewModel(
    private val randomUsersRepository: RandomUsersRepository
) : ViewModel() {

    val randomUsers by lazyDeferred {
        buildRandomUsers()
    }

    suspend fun removeUser(randomUser: RandomUserEntry) {
        randomUsersRepository.deleteRandomUserWithId(randomUser.getId())
    }

    fun buildFilteredRandomUsers(
        search: String
    ): LiveData<PagedList<RandomUserEntry>> {
        val factory: DataSource.Factory<Int, RandomUserEntry> = filterUsersWithSearch("%$search%")
        val pagedListBuilder: LivePagedListBuilder<Int, RandomUserEntry> =
            LivePagedListBuilder<Int, RandomUserEntry>(factory, PAGES_RANDOM_USERS_SIZE)
        return pagedListBuilder.build()
    }

    private fun filterUsersWithSearch(
        search: String
    ): DataSource.Factory<Int, RandomUserEntry> {
        return randomUsersRepository.filterUsersWithSearch(search)
    }

    private fun buildRandomUsers(): LiveData<PagedList<RandomUserEntry>> {
        val factory: DataSource.Factory<Int, RandomUserEntry> = randomUsersRepository.getRandomUsers()
        val pagedListBuilder: LivePagedListBuilder<Int, RandomUserEntry> =
            LivePagedListBuilder<Int, RandomUserEntry>(factory, PAGES_RANDOM_USERS_SIZE)
        pagedListBuilder.setBoundaryCallback(RandomUsersBoundaryCallback(randomUsersRepository))
        return pagedListBuilder.build()
    }
}
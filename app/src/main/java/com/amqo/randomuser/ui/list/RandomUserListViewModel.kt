package com.amqo.randomuser.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUsersWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.data.repository.RandomUsersRepository.Companion.PAGES_RANDOM_USERS_SIZE
import com.amqo.randomuser.internal.lazyDeferred

class RandomUserListViewModel(
    private val randomUserListBoundaryCallback: RandomUserListBoundaryCallback,
    private val getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase,
    private val deleteRandomUsersWithIdUseCase: DeleteRandomUsersWithIdUseCase,
    private val searchRandomUsersUseCase: SearchRandomUsersUseCase
) : ViewModel() {

    val randomUsers by lazyDeferred {
        getRandomUsers()
    }

    fun removeUser(randomUser: RandomUserEntry) {
        deleteRandomUsersWithIdUseCase.execute(randomUser.getId())
    }

    fun getFilteredRandomUsers(
        search: String
    ): LiveData<PagedList<RandomUserEntry>> {
        val factory: DataSource.Factory<Int, RandomUserEntry> = filterUsersWithSearch("%$search%")
        val pagedListBuilder: LivePagedListBuilder<Int, RandomUserEntry> =
            LivePagedListBuilder<Int, RandomUserEntry>(factory, PAGES_RANDOM_USERS_SIZE)
        return pagedListBuilder.build()
    }

    // Private functions

    private fun filterUsersWithSearch(
        search: String
    ): DataSource.Factory<Int, RandomUserEntry> {
        return searchRandomUsersUseCase.execute(search)
    }

    private fun getRandomUsers(): LiveData<PagedList<RandomUserEntry>> {
        val factory: DataSource.Factory<Int, RandomUserEntry> = getLocalRandomUsersUseCase.execute()
        val pagedListBuilder: LivePagedListBuilder<Int, RandomUserEntry> =
            LivePagedListBuilder<Int, RandomUserEntry>(factory, PAGES_RANDOM_USERS_SIZE)
        pagedListBuilder.setBoundaryCallback(randomUserListBoundaryCallback)
        return pagedListBuilder.build()
    }
}
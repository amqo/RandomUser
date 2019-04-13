package com.amqo.randomuser.ui.list

import androidx.paging.PagedList
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.db.entity.RandomUserEntry
import kotlinx.coroutines.runBlocking

class RandomUsersBoundaryCallback(
    private val randomUsersRepository: RandomUsersRepository
) : PagedList.BoundaryCallback<RandomUserEntry>() {

    override fun onItemAtEndLoaded(itemAtEnd: RandomUserEntry) {
        getNewRandomUsers()
    }

    override fun onZeroItemsLoaded() {
        getNewRandomUsers()
    }

    private fun getNewRandomUsers() {
        runBlocking {
            randomUsersRepository.getNewRandomUsers()
        }
    }
}
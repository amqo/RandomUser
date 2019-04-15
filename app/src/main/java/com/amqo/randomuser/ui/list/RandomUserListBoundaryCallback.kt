package com.amqo.randomuser.ui.list

import androidx.paging.PagedList
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.GetNewRandomUsersUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RandomUserListBoundaryCallback(
    private val getNewRandomUsersUseCase: GetNewRandomUsersUseCase
) : PagedList.BoundaryCallback<RandomUserEntry>() {

    override fun onItemAtEndLoaded(itemAtEnd: RandomUserEntry) {
        getNewRandomUsers()
    }

    override fun onZeroItemsLoaded() {
        getNewRandomUsers()
    }

    private fun getNewRandomUsers() =
        GlobalScope.launch {
            getNewRandomUsersUseCase.execute()
        }
}
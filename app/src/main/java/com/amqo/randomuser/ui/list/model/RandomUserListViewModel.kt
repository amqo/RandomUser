package com.amqo.randomuser.ui.list.model

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.RecoverRandomUserUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.data.repository.RandomUsersRepository.Companion.PAGES_RANDOM_USERS_SIZE
import com.amqo.randomuser.internal.lazyDeferred
import com.amqo.randomuser.ui.list.RandomUserListBoundaryCallback

class RandomUserListViewModel(
    private val randomUserListBoundaryCallback: RandomUserListBoundaryCallback,
    private val getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase,
    private val deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase,
    private val searchRandomUsersUseCase: SearchRandomUsersUseCase,
    private val recoverRandomUserUseCase: RecoverRandomUserUseCase,
    private val livePagedListBuilderFactory: LivePagedListBuilderFactory<RandomUserEntry>
) : ViewModel() {

    val randomUsers by lazyDeferred {
        getRandomUsersBuilder().build()
    }

    fun removeUser(randomUser: RandomUserEntry) =
        deleteRandomUserWithIdUseCase.execute(randomUser.getId())

    fun recoverUser(randomUser: RandomUserEntry) =
        recoverRandomUserUseCase.execute(randomUser)

    fun getFilteredRandomUsersBuilder(
        search: String
    ): LivePagedListBuilder<Int, RandomUserEntry> =
        livePagedListBuilderFactory.create(
            PAGES_RANDOM_USERS_SIZE, searchRandomUsersUseCase.execute("%$search%"))


    // Private functions

    private fun getRandomUsersBuilder(): LivePagedListBuilder<Int, RandomUserEntry> =
        livePagedListBuilderFactory.create(
            PAGES_RANDOM_USERS_SIZE,
            randomUserListBoundaryCallback, getLocalRandomUsersUseCase.execute())
}
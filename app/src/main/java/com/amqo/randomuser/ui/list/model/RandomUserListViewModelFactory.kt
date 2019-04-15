package com.amqo.randomuser.ui.list.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.RecoverRandomUserUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.ui.list.RandomUserListBoundaryCallback

class RandomUserListViewModelFactory(
    private val randomUserListBoundaryCallback: RandomUserListBoundaryCallback,
    private val getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase,
    private val deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase,
    private val searchRandomUsersUseCase: SearchRandomUsersUseCase,
    private val recoverRandomUserUseCase: RecoverRandomUserUseCase,
    private val livePagedListBuilderFactory: LivePagedListBuilderFactory<RandomUserEntry>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserListViewModel(
            randomUserListBoundaryCallback, getLocalRandomUsersUseCase,
            deleteRandomUserWithIdUseCase, searchRandomUsersUseCase,
            recoverRandomUserUseCase, livePagedListBuilderFactory
        ) as T
    }
}
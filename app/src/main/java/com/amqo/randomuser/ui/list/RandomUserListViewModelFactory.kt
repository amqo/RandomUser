package com.amqo.randomuser.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.domain.DeleteRandomUsersWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase

class RandomUserListViewModelFactory(
    private val randomUserListBoundaryCallback: RandomUserListBoundaryCallback,
    private val getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase,
    private val deleteRandomUsersWithIdUseCase: DeleteRandomUsersWithIdUseCase,
    private val searchRandomUsersUseCase: SearchRandomUsersUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserListViewModel(
            randomUserListBoundaryCallback, getLocalRandomUsersUseCase,
            deleteRandomUsersWithIdUseCase, searchRandomUsersUseCase) as T
    }
}
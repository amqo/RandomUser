package com.amqo.randomuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.domain.DeleteRandomUsersWithIdUseCase

class RandomUserDetailActivityViewModelFactory(
    private val deleteRandomUsersWithIdUseCase: DeleteRandomUsersWithIdUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserDetailActivityViewModel(deleteRandomUsersWithIdUseCase) as T
    }
}
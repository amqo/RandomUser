package com.amqo.randomuser.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.repository.RandomUsersRepository

class RandomUserListViewModelFactory(
    private val randomUsersRepository: RandomUsersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserListViewModel(randomUsersRepository) as T
    }
}
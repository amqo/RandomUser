package com.amqo.randomuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.repository.RandomUsersRepository

class RandomUserViewModelFactory(
    private val userId: String,
    private val randomUsersRepository: RandomUsersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserViewModel(userId, randomUsersRepository) as T
    }
}
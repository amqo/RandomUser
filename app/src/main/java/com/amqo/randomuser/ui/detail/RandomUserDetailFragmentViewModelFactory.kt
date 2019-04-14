package com.amqo.randomuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.ui.base.ResourceProvider

class RandomUserDetailFragmentViewModelFactory(
    private val userId: String,
    private val getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomUserDetailFragmentViewModel(userId, getRandomUserWithIdUseCase, resourceProvider) as T
    }
}
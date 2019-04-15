package com.amqo.randomuser.ui.detail.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.ui.base.ResourcesProvider

class RandomUserDetailFragmentViewModelFactory(
    private val userId: String,
    private val getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase,
    private val resourcesProvider: ResourcesProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RandomUserDetailFragmentViewModel(
            userId, getRandomUserWithIdUseCase, resourcesProvider
        ) as T
}
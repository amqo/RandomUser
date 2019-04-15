package com.amqo.randomuser.ui.detail.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase

class RandomUserDetailActivityViewModelFactory(
    private val deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RandomUserDetailActivityViewModel(deleteRandomUserWithIdUseCase) as T
}
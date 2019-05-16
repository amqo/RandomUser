package com.amqo.randomuser.ui.detail.model

import androidx.lifecycle.ViewModel
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase

class RandomUserDetailActivityViewModel(
    private val deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase
) : ViewModel() {

    fun removeUser(userId: String) = deleteRandomUserWithIdUseCase.execute(userId)
}
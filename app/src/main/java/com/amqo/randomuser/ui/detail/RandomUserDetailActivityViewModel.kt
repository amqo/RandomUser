package com.amqo.randomuser.ui.detail

import androidx.lifecycle.ViewModel
import com.amqo.randomuser.data.domain.DeleteRandomUsersWithIdUseCase

class RandomUserDetailActivityViewModel(
    private val deleteRandomUsersWithIdUseCase: DeleteRandomUsersWithIdUseCase
) : ViewModel() {

    fun removeUser(userId: String) {
        deleteRandomUsersWithIdUseCase.execute(userId)
    }
}
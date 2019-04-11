package com.amqo.randomuser.ui.detail

import androidx.lifecycle.ViewModel
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.internal.lazyDeferred

class RandomUserViewModel(
    private val userId: String,
    private val randomUsersRepository: RandomUsersRepository
) : ViewModel() {

    val randomUser by lazyDeferred {
        randomUsersRepository.getRandomUserWithId(userId)
    }
}
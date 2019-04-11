package com.amqo.randomuser.ui.list

import androidx.lifecycle.ViewModel
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.internal.lazyDeferred

class RandomUserListViewModel(
    private val randomUsersRepository: RandomUsersRepository
) : ViewModel() {

    val randomUsers by lazyDeferred {
        randomUsersRepository.getRandomUsers(40)
    }
}
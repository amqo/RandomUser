package com.amqo.randomuser.data.domain

import androidx.lifecycle.LiveData
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository

class GetRandomUserWithIdUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(
        id: String
    ) : LiveData<out RandomUserEntry> {
        return randomUsersRepository.getRandomUserWithId(id)
    }
}
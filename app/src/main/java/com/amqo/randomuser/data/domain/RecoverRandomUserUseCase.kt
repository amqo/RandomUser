package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository

class RecoverRandomUserUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(
        randomUser: RandomUserEntry
    ) {
        randomUsersRepository.recoverRandomUser(randomUser)
    }
}
package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository

class GetNewRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    suspend fun execute() {
        randomUsersRepository.getNewRandomUsers()
    }
}
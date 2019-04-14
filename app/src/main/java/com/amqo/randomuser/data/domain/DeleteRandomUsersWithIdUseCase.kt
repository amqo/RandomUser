package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository

class DeleteRandomUsersWithIdUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(
        id: String
    ) = randomUsersRepository.deleteRandomUserWithId(id)
}
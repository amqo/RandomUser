package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository

class GetLocalRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute() = randomUsersRepository.getRandomUsers()
}
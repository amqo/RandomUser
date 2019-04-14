package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository

class SearchRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(
        search: String
    ) = randomUsersRepository.searchRandomUsers(search)
}
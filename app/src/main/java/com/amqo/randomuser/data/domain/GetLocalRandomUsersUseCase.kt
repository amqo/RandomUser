package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository

class GetLocalRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(): DataSource.Factory<Int, RandomUserEntry> {
        return randomUsersRepository.getRandomUsers()
    }
}
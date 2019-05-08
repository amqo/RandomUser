package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository

class SearchRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {
    fun execute(
        search: String
    ) : DataSource.Factory<Int, RandomUserEntry> {
        return randomUsersRepository.searchRandomUsers(search)
    }
}
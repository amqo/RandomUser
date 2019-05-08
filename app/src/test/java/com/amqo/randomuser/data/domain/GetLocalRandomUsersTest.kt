package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetLocalRandomUsersTest {

    private val repository = mockk<RandomUsersRepository> {
        every { getRandomUsers() } returns randomUsersFactory
    }
    private val randomUsersFactory = mockk<DataSource.Factory<Int, RandomUserEntry>>()

    @InjectMockKs
    private var getLocalRandomUsersUseCase = GetLocalRandomUsersUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When GetLocalRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getRandomUsers function is called"
    )
    fun getLocalRandomUsers() {
        getLocalRandomUsersUseCase.execute()

        runBlocking {
            verify { repository.getRandomUsers() }
        }
    }
}
package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetLocalRandomUsersTest {

    private val repository = mockk<RandomUsersRepository>()
    private val randomUsersFactory = mockk<DataSource.Factory<Int, RandomUserEntry>>()

    private lateinit var getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getLocalRandomUsersUseCase = GetLocalRandomUsersUseCase(repository)
    }

    @BeforeEach
    fun reset() {
        clearMocks(repository, randomUsersFactory)
    }

    @Test
    @DisplayName(
        "When GetLocalRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getRandomUsers function is called"
    )
    fun getLocalRandomUsers() {
        every { repository.getRandomUsers() } returns randomUsersFactory
        getLocalRandomUsersUseCase.execute()

        runBlocking {
            verify { repository.getRandomUsers() }
        }
    }
}
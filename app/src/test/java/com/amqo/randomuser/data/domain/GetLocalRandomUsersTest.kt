package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*


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

    @BeforeEach
    fun reset() {
        clearMocks(randomUsersFactory)
    }

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
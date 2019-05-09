package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetNewRandomUsersTest {

    private val repository = mockk<RandomUsersRepository>(relaxed = true)

    @InjectMockKs
    private lateinit var getNewRandomUsersUseCase: GetNewRandomUsersUseCase

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun reset() {
        clearAllMocks()
    }

    @Test
    @DisplayName(
        "When GetNewRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getNewRandomUsers function is called"
    )
    fun getLocalRandomUsers() {
        runBlocking {
            getNewRandomUsersUseCase.execute()

            verify {
                runBlocking {
                    repository.getNewRandomUsers()
                }
            }
        }
    }
}
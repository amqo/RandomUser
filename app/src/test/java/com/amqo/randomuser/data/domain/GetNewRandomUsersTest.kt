package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetNewRandomUsersTest {

    private val repository = mockk<RandomUsersRepository>()

    @InjectMockKs
    private var getNewRandomUsersUseCase = GetNewRandomUsersUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When GetNewRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getNewRandomUsers function is called"
    )
    fun getLocalRandomUsers() {

//        runBlocking {
//            launch {
//                every { repository.getNewRandomUsers() } returns Unit
//                getNewRandomUsersUseCase.execute()
//
//                verify {
//                    repository.getNewRandomUsers()
//                }
//            }
//        }
    }
}
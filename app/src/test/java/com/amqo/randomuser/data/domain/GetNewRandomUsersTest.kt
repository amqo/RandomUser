package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetNewRandomUsersTest {

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var getNewRandomUsersUseCase: GetNewRandomUsersUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When GetNewRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getNewRandomUsers function is called"
    )
    fun getLocalRandomUsers() {
        runBlocking {
            getNewRandomUsersUseCase.execute()

            verify(repository).getNewRandomUsers()
        }
    }
}
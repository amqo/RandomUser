package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetLocalRandomUsersTest {

    @Mock lateinit var repository: RandomUsersRepository
    @Mock lateinit var randomUsersFactory: DataSource.Factory<Int, RandomUserEntry>

    @InjectMocks internal lateinit var getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When GetLocalRandomUsersUseCase is called, " +
                "Then RandomUsersRepository getRandomUsers function is called"
    )
    fun getLocalRandomUsers() {
        Mockito.`when`(repository.getRandomUsers()).thenAnswer { randomUsersFactory }
        getLocalRandomUsersUseCase.execute()

        runBlocking {
            verify(repository).getRandomUsers()
        }
    }
}
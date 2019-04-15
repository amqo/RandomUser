package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetNewRandomUsersTest {

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var getNewRandomUsersUseCase: GetNewRandomUsersUseCase

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getLocalRandomUsers() {
        runBlocking {
            getNewRandomUsersUseCase.execute()

            verify(repository).getNewRandomUsers()
        }
    }
}
package com.amqo.randomuser.data.domain

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetLocalRandomUsersTest {

    @Mock lateinit var repository: RandomUsersRepository
    @Mock lateinit var randomUsersFactory: DataSource.Factory<Int, RandomUserEntry>

    @InjectMocks internal lateinit var getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getLocalRandomUsers() {
        Mockito.`when`(repository.getRandomUsers()).thenAnswer { randomUsersFactory }
        getLocalRandomUsersUseCase.execute()

        runBlocking {
            verify(repository).getRandomUsers()
        }
    }
}
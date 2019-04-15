package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RecoverRandomUserTest {

    @Mock lateinit var repository: RandomUsersRepository
    @Mock lateinit var randomUser: RandomUserEntry

    @InjectMocks internal lateinit var recoverRandomUserUseCase: RecoverRandomUserUseCase

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun deleteRandomUserWithId() {
        recoverRandomUserUseCase.execute(randomUser)

        verify(repository).recoverRandomUser(randomUser)
    }
}
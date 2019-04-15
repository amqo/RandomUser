package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class DeleteRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun deleteRandomUserWithId() {
        deleteRandomUserWithIdUseCase.execute(dummyUserId)

        verify(repository).deleteRandomUserWithId(dummyUserId)
    }
}
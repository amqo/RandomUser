package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun deleteRandomUserWithId() {
        deleteRandomUserWithIdUseCase.execute(dummyUserId)

        verify(repository).deleteRandomUserWithId(dummyUserId)
    }
}
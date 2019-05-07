package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecoverRandomUserTest {

    @Mock lateinit var repository: RandomUsersRepository
    @Mock lateinit var randomUser: RandomUserEntry

    @InjectMocks internal lateinit var recoverRandomUserUseCase: RecoverRandomUserUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When RecoverRandomUserUseCase is called with a RandomUserEntry, " +
                "Then RandomUsersRepository recoverRandomUser function is called with the same RandomUserEntry"
    )
    fun deleteRandomUserWithId() {
        recoverRandomUserUseCase.execute(randomUser)

        verify(repository).recoverRandomUser(randomUser)
    }
}
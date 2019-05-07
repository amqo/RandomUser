package com.amqo.randomuser.data.domain

import androidx.lifecycle.MutableLiveData
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()

    @Mock lateinit var repository: RandomUsersRepository
    @Mock lateinit var randomUserLiveData: MutableLiveData<RandomUserEntry>

    @InjectMocks internal lateinit var getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getRandomUserWithId() {
        Mockito.`when`(repository.getRandomUserWithId(dummyUserId)).thenAnswer { randomUserLiveData }
        getRandomUserWithIdUseCase.execute(dummyUserId)

        verify(repository).getRandomUserWithId(dummyUserId)
    }
}
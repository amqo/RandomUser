package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchRandomUsersTest {

    private val searchTerm = "dummy search"

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var searchRandomUsersUseCase: SearchRandomUsersUseCase

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun deleteRandomUserWithId() {
        searchRandomUsersUseCase.execute(searchTerm)

        verify(repository).searchRandomUsers(searchTerm)
    }
}
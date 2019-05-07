package com.amqo.randomuser.data.domain

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
class SearchRandomUsersTest {

    private val searchTerm = "dummy search"

    @Mock lateinit var repository: RandomUsersRepository

    @InjectMocks internal lateinit var searchRandomUsersUseCase: SearchRandomUsersUseCase

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When SearchRandomUsersUseCase is called with a SearchTerm, " +
                "Then RandomUsersRepository searchRandomUsers function is called with the same SearchTerm"
    )
    fun deleteRandomUserWithId() {
        searchRandomUsersUseCase.execute(searchTerm)

        verify(repository).searchRandomUsers(searchTerm)
    }
}
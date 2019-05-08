package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchRandomUsersTest {

    private val searchTerm = "dummy search"
    private val repository = mockk<RandomUsersRepository> {
        every { searchRandomUsers(searchTerm) } returns mockk()
    }

    @InjectMockKs
    private var searchRandomUsersUseCase = SearchRandomUsersUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When SearchRandomUsersUseCase is called with a SearchTerm, " +
                "Then RandomUsersRepository searchRandomUsers function is called with the same SearchTerm"
    )
    fun deleteRandomUserWithId() {
        searchRandomUsersUseCase.execute(searchTerm)

        verify { repository.searchRandomUsers(searchTerm) }
    }
}
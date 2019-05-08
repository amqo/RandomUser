package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchRandomUsersTest {

    private val searchTerm = "dummy search"
    private val repository = mockk<RandomUsersRepository>(relaxed = true)

    @InjectMockKs
    private var searchRandomUsersUseCase = SearchRandomUsersUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @BeforeEach
    fun reset() {
        clearMocks(repository)
    }

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
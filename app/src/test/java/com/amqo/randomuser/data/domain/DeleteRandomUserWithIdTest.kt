package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val repository = mockk<RandomUsersRepository>(relaxed = true)

    @InjectMockKs
    private var deleteRandomUserWithIdUseCase = DeleteRandomUserWithIdUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @BeforeEach
    fun reset() {
        clearMocks(repository)
    }

    @Test
    @DisplayName(
        "When DeleteRandomUserWithIdUseCase is called with and ID, " +
                "Then RandomUsersRepository deleteRandomUserWithId function is called with the same ID"
    )
    fun deleteRandomUserWithId() {
        deleteRandomUserWithIdUseCase.execute(dummyUserId)

        verify { repository.deleteRandomUserWithId(dummyUserId) }
    }
}
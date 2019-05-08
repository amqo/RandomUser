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
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val repository = mockk<RandomUsersRepository> {
        every { deleteRandomUserWithId(dummyUserId) } returns mockk()
    }

    @InjectMockKs
    private var deleteRandomUserWithIdUseCase = DeleteRandomUserWithIdUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

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
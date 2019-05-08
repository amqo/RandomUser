package com.amqo.randomuser.data.domain

import com.amqo.randomuser.data.db.entity.RandomUserEntry
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
class RecoverRandomUserTest {

    private val repository = mockk<RandomUsersRepository> {
        every { recoverRandomUser(any()) } returns mockk()
    }
    private val randomUser = mockk<RandomUserEntry>()

    @InjectMockKs
    private var recoverRandomUserUseCase = RecoverRandomUserUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When RecoverRandomUserUseCase is called with a RandomUserEntry, " +
                "Then RandomUsersRepository recoverRandomUser function is called with the same RandomUserEntry"
    )
    fun deleteRandomUserWithId() {
        recoverRandomUserUseCase.execute(randomUser)

        verify { repository.recoverRandomUser(randomUser) }
    }
}
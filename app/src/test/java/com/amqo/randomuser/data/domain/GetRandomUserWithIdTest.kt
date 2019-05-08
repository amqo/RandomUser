package com.amqo.randomuser.data.domain

import androidx.lifecycle.MutableLiveData
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val repository = mockk<RandomUsersRepository> {
        every { getRandomUserWithId(dummyUserId) } returns randomUserLiveData
    }
    private val randomUserLiveData = mockk<MutableLiveData<RandomUserEntry>>()

    @InjectMockKs
    private var getRandomUserWithIdUseCase = GetRandomUserWithIdUseCase(repository)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When GetRandomUserWithIdUseCase is called with an ID, " +
                "Then RandomUsersRepository getRandomUserWithId function is called with the same ID"
    )
    fun getRandomUserWithId() {
        getRandomUserWithIdUseCase.execute(dummyUserId)

        every { repository.getRandomUserWithId(dummyUserId) }
    }
}
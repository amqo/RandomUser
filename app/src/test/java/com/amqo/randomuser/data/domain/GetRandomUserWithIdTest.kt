package com.amqo.randomuser.data.domain

import androidx.lifecycle.MutableLiveData
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.repository.RandomUsersRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetRandomUserWithIdTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val repository = mockk<RandomUsersRepository>()
    private val randomUserLiveData = mockk<MutableLiveData<RandomUserEntry>>()

    @InjectMockKs
    private lateinit var getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun reset() {
        clearAllMocks()
    }

    @Test
    @DisplayName(
        "When GetRandomUserWithIdUseCase is called with an ID, " +
                "Then RandomUsersRepository getRandomUserWithId function is called with the same ID"
    )
    fun getRandomUserWithId() {
        every { repository.getRandomUserWithId(dummyUserId) } returns randomUserLiveData
        getRandomUserWithIdUseCase.execute(dummyUserId)

        every { repository.getRandomUserWithId(dummyUserId) }
    }
}
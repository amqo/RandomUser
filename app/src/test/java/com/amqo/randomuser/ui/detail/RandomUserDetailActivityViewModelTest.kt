package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModel
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
class RandomUserDetailActivityViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()
    private val deleteRandomUserWithIdUseCase = mockk<DeleteRandomUserWithIdUseCase> {
        every { execute(dummyUserId) } returns mockk()
    }

    @InjectMockKs
    private var randomUserDetailActivityViewModel =
        RandomUserDetailActivityViewModel(deleteRandomUserWithIdUseCase)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "When RandomUserDetailActivityViewModel removeUser function is called with an ID, " +
                "Then DeleteRandomUserWithIdUseCase is executed with the same ID"
    )
    fun removeUser() {
        randomUserDetailActivityViewModel.removeUser(dummyUserId)

        verify { deleteRandomUserWithIdUseCase.execute(dummyUserId) }
    }
}
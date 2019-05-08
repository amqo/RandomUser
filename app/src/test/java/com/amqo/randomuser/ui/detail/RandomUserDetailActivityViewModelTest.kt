package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserDetailActivityViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()
    private val deleteRandomUserWithIdUseCase = mockk<DeleteRandomUserWithIdUseCase>(relaxed = true)

    private lateinit var randomUserDetailActivityViewModel: RandomUserDetailActivityViewModel

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        randomUserDetailActivityViewModel =
            RandomUserDetailActivityViewModel(deleteRandomUserWithIdUseCase)
    }

    @BeforeEach
    fun reset() {
        clearMocks(deleteRandomUserWithIdUseCase)
    }

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
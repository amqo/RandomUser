package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModel
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserDetailActivityViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()

    @Mock lateinit var deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase

    @InjectMocks
    internal lateinit var randomUserDetailActivityViewModel: RandomUserDetailActivityViewModel

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When RandomUserDetailActivityViewModel removeUser function is called with an ID, " +
                "Then DeleteRandomUserWithIdUseCase is executed with the same ID"
    )
    fun removeUser() {
        randomUserDetailActivityViewModel.removeUser(dummyUserId)

        verify(deleteRandomUserWithIdUseCase).execute(dummyUserId)
    }
}
package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class RandomUserDetailActivityViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()

    @Mock
    lateinit var deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase

    @InjectMocks
    internal lateinit var randomUserDetailActivityViewModel: RandomUserDetailActivityViewModel

    @Before
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun removeUser() {
        randomUserDetailActivityViewModel.removeUser(dummyUserId)

        verify(deleteRandomUserWithIdUseCase).execute(dummyUserId)
    }
}
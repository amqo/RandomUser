package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.data.network.response.Registered
import com.amqo.randomuser.ui.base.ResourcesProvider
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserDetailFragmentViewModelTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val dummyMail = "dummyMail@example.com"
    private val dummyRegisteredDate = "005-01-26T13:08:18Z"

    @Mock lateinit var getRandomUserWithIdUseCase: GetRandomUserWithIdUseCase
    @Mock lateinit var resourcesProvider: ResourcesProvider
    @Mock lateinit var randomUser: RandomUserEntry
    @Mock lateinit var randomUserRegistered: Registered

    private lateinit var randomUserDetailFragmentViewModel: RandomUserDetailFragmentViewModel

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
        randomUserDetailFragmentViewModel = RandomUserDetailFragmentViewModel(
            dummyUserId, getRandomUserWithIdUseCase, resourcesProvider)
    }

    @Test
    fun randomUserInit() {
        runBlocking {
            randomUserDetailFragmentViewModel.randomUser.await()

            Mockito.verify(getRandomUserWithIdUseCase).execute(dummyUserId)
        }
    }

    @Test
    fun getMailFormatted() {
        Mockito.`when`(randomUser.email).thenReturn(dummyMail)
        randomUserDetailFragmentViewModel.getMailFormatted(randomUser)

        Mockito.verify(resourcesProvider).formatStringColorUnderline(android.R.color.holo_blue_dark, dummyMail)
    }

    @Test
    fun getRegisteredMessage() {
        Mockito.`when`(randomUser.registered).thenReturn(randomUserRegistered)
        Mockito.`when`(randomUserRegistered.date).thenReturn(dummyRegisteredDate)
        randomUserDetailFragmentViewModel.getRegisteredMessage(randomUser)

        Mockito.verify(resourcesProvider).formatDateColor(
            R.color.colorPrimary, "Registered since ", dummyRegisteredDate)
    }
}
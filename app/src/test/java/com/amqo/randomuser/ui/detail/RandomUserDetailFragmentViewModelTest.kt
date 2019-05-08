package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.data.network.response.Registered
import com.amqo.randomuser.ui.base.ResourcesProvider
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserDetailFragmentViewModelTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val dummyMail = "dummyMail@example.com"
    private val dummyRegisteredDate = "005-01-26T13:08:18Z"

    private val getRandomUserWithIdUseCase = mockk<GetRandomUserWithIdUseCase> {
        every { execute(dummyUserId) } returns mockk()
    }
    private val resourcesProvider = mockk<ResourcesProvider> {
        every { formatDateColor(R.color.colorPrimary, "Registered since ", dummyRegisteredDate) } returns mockk()
        every { formatStringColorUnderline(android.R.color.holo_blue_dark, dummyMail) } returns mockk()
    }
    private val randomUser = mockk<RandomUserEntry>()
    private val randomUserRegistered = mockk<Registered>()

    @InjectMockKs
    private var randomUserDetailFragmentViewModel =
        RandomUserDetailFragmentViewModel(dummyUserId, getRandomUserWithIdUseCase, resourcesProvider)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @DisplayName(
        "Given RandomUserDetailFragmentViewModel constructor called with a RandomUserEntry ID," +
                "When RandomUserDetailFragmentViewModel randomUser is referenced, " +
                "Then GetRandomUserWithIdUseCase is executed with that RandomUserEntry ID"
    )
    fun randomUserInit() {
        runBlocking {
            randomUserDetailFragmentViewModel.randomUser.await()

            verify { getRandomUserWithIdUseCase.execute(dummyUserId) }
        }
    }

    @Test
    @DisplayName(
        "When RandomUserDetailFragmentViewModel getMailFormatted is called with a RandomUserEntry, " +
                "Then ResourcesProvider formatStringColorUnderline function is called with that RandomUserEntry EMAIL"
    )
    fun getMailFormatted() {
        every { randomUser.email } returns dummyMail
        randomUserDetailFragmentViewModel.getMailFormatted(randomUser)

        verify { resourcesProvider.formatStringColorUnderline(android.R.color.holo_blue_dark, dummyMail) }
    }

    @Test
    @DisplayName(
        "When RandomUserDetailFragmentViewModel getRegisteredMessage is called with a RandomUserEntry, " +
                "Then ResourcesProvider formatDateColor function is called with that RandomUserEntry DATE"
    )
    fun getRegisteredMessage() {
        every { randomUser.registered } returns randomUserRegistered
        every { randomUserRegistered.date } returns dummyRegisteredDate
        randomUserDetailFragmentViewModel.getRegisteredMessage(randomUser)

        verify { resourcesProvider.formatDateColor(
            R.color.colorPrimary, "Registered since ", dummyRegisteredDate)
        }
    }
}
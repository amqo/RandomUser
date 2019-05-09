package com.amqo.randomuser.ui.detail

import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.GetRandomUserWithIdUseCase
import com.amqo.randomuser.data.network.response.Registered
import com.amqo.randomuser.ui.base.ResourcesProvider
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserDetailFragmentViewModelTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val dummyMail = "dummyMail@example.com"
    private val dummyRegisteredDate = "005-01-26T13:08:18Z"

    private val getRandomUserWithIdUseCase = mockk<GetRandomUserWithIdUseCase>(relaxed = true)
    private val resourcesProvider = mockk<ResourcesProvider>(relaxed = true)
    private val randomUser = mockk<RandomUserEntry>()
    private val randomUserRegistered = mockk<Registered>()

    private lateinit var randomUserDetailFragmentViewModel: RandomUserDetailFragmentViewModel

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        randomUserDetailFragmentViewModel = RandomUserDetailFragmentViewModel(
            dummyUserId, getRandomUserWithIdUseCase, resourcesProvider)
    }

    @BeforeEach
    fun reset() {
        clearAllMocks()
    }

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
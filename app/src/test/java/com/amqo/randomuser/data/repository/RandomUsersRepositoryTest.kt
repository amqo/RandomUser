package com.amqo.randomuser.data.repository

import com.amqo.randomuser.data.db.RandomUsersDao
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.network.RandomUsersNetworkDataSource
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUsersRepositoryTest {

    private val dummyUserId = UUID.randomUUID().toString()
    private val searchTerm = "dummy search"

    private val randomUsersDao = mockk<RandomUsersDao>(relaxed = true)
    private val randomUsersNetworkDataSource = mockk<RandomUsersNetworkDataSource>(relaxed = true)
    private val randomUser = mockk<RandomUserEntry>()

    private lateinit var randomUsersRepository: RandomUsersRepository

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        randomUsersRepository = RandomUsersRepositoryImpl(randomUsersDao, randomUsersNetworkDataSource)
    }

    @BeforeEach
    fun reset() {
        clearAllMocks()
    }

    @Test
    @DisplayName(
        "When RandomUsersRepository getNewRandomUsers function is called, " +
                "Then RandomUsersNetworkDataSource fetchRandomUsers function is called the correct constant"
    )
    fun getNewRandomUsers() {
        runBlocking {
            randomUsersRepository.getNewRandomUsers()
        }
        verify {
            runBlocking {
                randomUsersNetworkDataSource.fetchRandomUsers(RandomUsersRepository.PAGES_RANDOM_USERS_SIZE)
            }
        }
    }

    @Test
    @DisplayName(
        "When RandomUsersRepository recoverRandomUser function is called with a RandomUserEntry, " +
                "Then RandomUsersDao recover function is called with that RandomUserEntry ID"
    )
    fun recoverRandomUser() {
        every { randomUser.getId() } returns dummyUserId
        randomUsersRepository.recoverRandomUser(randomUser)

        verify { randomUsersDao.recover(dummyUserId) }
    }

    @Test
    @DisplayName(
        "When RandomUsersRepository getRandomUserWithId function is called with an ID, " +
                "Then RandomUsersDao getWithId is called with the same ID"
    )
    fun getRandomUserWithId() {
        randomUsersRepository.getRandomUserWithId(dummyUserId)

        verify { randomUsersDao.getWithId(dummyUserId) }
    }

    @Test
    @DisplayName(
        "When RandomUsersRepository deleteRandomUserWithId function is called with an ID, " +
                "Then RandomUsersDao deleteWithId is called with the same ID"
    )
    fun deleteRandomUserWithId() {
        randomUsersRepository.deleteRandomUserWithId(dummyUserId)

        verify { randomUsersDao.deleteWithId(dummyUserId) }
    }

    @Test
    @DisplayName(
        "When RandomUsersRepository searchRandomUsers function is called with a SearchTerm, " +
                "Then RandomUsersDao getWithSearch is called with the same SearchTerm"
    )
    fun searchRandomUsers() {
        randomUsersRepository.searchRandomUsers(searchTerm)

        verify { randomUsersDao.getWithSearch(searchTerm) }
    }
}

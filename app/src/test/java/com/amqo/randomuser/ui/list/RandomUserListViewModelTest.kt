package com.amqo.randomuser.ui.list

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.RecoverRandomUserUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.ui.list.model.LivePagedListBuilderFactory
import com.amqo.randomuser.ui.list.model.RandomUserListViewModel
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserListViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()
    private val searchTerm = "dummy search"

    private val randomUserListBoundaryCallback = mockk<RandomUserListBoundaryCallback>()
    private val getLocalRandomUsersUseCase = mockk<GetLocalRandomUsersUseCase> {
        every { execute() } returns dataSourceFactory
    }
    private val deleteRandomUserWithIdUseCase = mockk<DeleteRandomUserWithIdUseCase>(relaxed = true)
    private val searchRandomUsersUseCase = mockk<SearchRandomUsersUseCase> {
        every { execute("%$searchTerm%") } returns dataSourceFactory
    }
    private val recoverRandomUserUseCase = mockk<RecoverRandomUserUseCase>(relaxed = true)

    private val livePagedListBuilderFactory = mockk<LivePagedListBuilderFactory<RandomUserEntry>> {
        every {
            create(
                RandomUsersRepository.PAGES_RANDOM_USERS_SIZE,
                randomUserListBoundaryCallback, dataSourceFactory
            )
        } returns mockk(relaxed = true)
        every { create(any(), any()) } returns mockk()
    }

    private val dataSourceFactory = mockk<DataSource.Factory<Int, RandomUserEntry>>()
    private val randomUser = mockk<RandomUserEntry> {
        every { getId() } returns dummyUserId
    }

    @InjectMockKs
    private var randomUserListViewModel = RandomUserListViewModel(
        randomUserListBoundaryCallback, getLocalRandomUsersUseCase,
        deleteRandomUserWithIdUseCase, searchRandomUsersUseCase, recoverRandomUserUseCase,
        livePagedListBuilderFactory)

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @BeforeEach
    fun reset() {
        clearMocks(randomUserListBoundaryCallback, deleteRandomUserWithIdUseCase,
            recoverRandomUserUseCase, dataSourceFactory)
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel randomUsers is referenced, " +
                "Then LivePagedListBuilderFactory is created"
    )
    fun randomUsersInit() {
        runBlocking {
            randomUserListViewModel.randomUsers.await()

            verify { livePagedListBuilderFactory.create(any(), any(), any()) }
        }
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel removeUser is called with a RandomUserEntry, " +
                "Then DeleteRandomUserWithIdUseCase is executed with the same RandomUserEntry ID"
    )
    fun removeUser() {
        randomUserListViewModel.removeUser(randomUser)

        verify { deleteRandomUserWithIdUseCase.execute(dummyUserId) }
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel recoverUser is called with a RandomUserEntry, " +
                "Then RecoverRandomUserUseCase is executed with the same RandomUserEntry"
    )
    fun recoverUser() {
        randomUserListViewModel.recoverUser(randomUser)

        verify { recoverRandomUserUseCase.execute(randomUser) }
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel getFilteredRandomUsersBuilder is called with a SearchTerm, " +
                "Then SearchRandomUsersUseCase is executed using that SearchTerm"
    )
    fun getFilteredUsers() {
        randomUserListViewModel.getFilteredRandomUsersBuilder(searchTerm)

        verify { searchRandomUsersUseCase.execute("%$searchTerm%") }
    }
}
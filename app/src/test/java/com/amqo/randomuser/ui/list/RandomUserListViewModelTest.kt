package com.amqo.randomuser.ui.list

import androidx.paging.DataSource
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.RecoverRandomUserUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.ui.list.model.LivePagedListBuilderFactory
import com.amqo.randomuser.ui.list.model.RandomUserListViewModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserListViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()
    private val searchTerm = "dummy search"

    private val randomUserListBoundaryCallback = mockk<RandomUserListBoundaryCallback>()
    private val getLocalRandomUsersUseCase = mockk<GetLocalRandomUsersUseCase>()
    private val deleteRandomUserWithIdUseCase = mockk<DeleteRandomUserWithIdUseCase>(relaxed = true)
    private val searchRandomUsersUseCase = mockk<SearchRandomUsersUseCase>()
    private val recoverRandomUserUseCase = mockk<RecoverRandomUserUseCase>(relaxed = true)

    private val livePagedListBuilderFactory = mockk<LivePagedListBuilderFactory<RandomUserEntry>>()

    private val dataSourceFactory = mockk<DataSource.Factory<Int, RandomUserEntry>>()
    private val randomUser = mockk<RandomUserEntry>()

    private lateinit var randomUserListViewModel: RandomUserListViewModel

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        randomUserListViewModel = RandomUserListViewModel(
            randomUserListBoundaryCallback, getLocalRandomUsersUseCase,
            deleteRandomUserWithIdUseCase, searchRandomUsersUseCase, recoverRandomUserUseCase,
            livePagedListBuilderFactory)
    }

    @BeforeEach
    fun reset() {
        clearMocks(
            randomUserListBoundaryCallback, getLocalRandomUsersUseCase, deleteRandomUserWithIdUseCase,
            searchRandomUsersUseCase, recoverRandomUserUseCase, dataSourceFactory,
            livePagedListBuilderFactory, randomUser
        )
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel randomUsers is referenced, " +
                "Then LivePagedListBuilderFactory is created"
    )
    fun randomUsersInit() {
        runBlocking {
            every { livePagedListBuilderFactory.create(any(), any(), any()) } returns mockk(relaxed = true)
            every { getLocalRandomUsersUseCase.execute() } returns dataSourceFactory
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
        every { randomUser.getId() } returns dummyUserId
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
        every { livePagedListBuilderFactory.create(any(), any()) } returns mockk()
        every { searchRandomUsersUseCase.execute("%$searchTerm%") } returns dataSourceFactory
        randomUserListViewModel.getFilteredRandomUsersBuilder(searchTerm)

        verify { searchRandomUsersUseCase.execute("%$searchTerm%") }
    }
}
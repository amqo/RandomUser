package com.amqo.randomuser.ui.list

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.DeleteRandomUserWithIdUseCase
import com.amqo.randomuser.data.domain.GetLocalRandomUsersUseCase
import com.amqo.randomuser.data.domain.RecoverRandomUserUseCase
import com.amqo.randomuser.data.domain.SearchRandomUsersUseCase
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.ui.list.model.LivePagedListBuilderFactory
import com.amqo.randomuser.ui.list.model.RandomUserListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RandomUserListViewModelTest {

    private val dummyUserId: String = UUID.randomUUID().toString()
    private val searchTerm = "dummy search"

    @Mock lateinit var randomUserListBoundaryCallback: RandomUserListBoundaryCallback
    @Mock lateinit var getLocalRandomUsersUseCase: GetLocalRandomUsersUseCase
    @Mock lateinit var deleteRandomUserWithIdUseCase: DeleteRandomUserWithIdUseCase
    @Mock lateinit var searchRandomUsersUseCase: SearchRandomUsersUseCase
    @Mock lateinit var recoverRandomUserUseCase: RecoverRandomUserUseCase
    @Mock lateinit var livePagedListBuilderFactory: LivePagedListBuilderFactory<RandomUserEntry>
    @Mock lateinit var dataSourceFactory: DataSource.Factory<Int, RandomUserEntry>

    @Mock lateinit var livePagedListBuilder: LivePagedListBuilder<Int, RandomUserEntry>
    @Mock lateinit var randomUser: RandomUserEntry

    @InjectMocks
    internal lateinit var randomUserListViewModel: RandomUserListViewModel

    @BeforeAll
    fun injectMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel randomUsers is referenced, " +
                "Then LivePagedListBuilder build function is called"
    )
    fun randomUsersInit() {
        runBlocking {
            Mockito.`when`(getLocalRandomUsersUseCase.execute()).thenReturn(dataSourceFactory)
            Mockito.`when`(
                livePagedListBuilderFactory.create(
                    RandomUsersRepository.PAGES_RANDOM_USERS_SIZE,
                    randomUserListBoundaryCallback, dataSourceFactory
                )
            ).thenReturn(livePagedListBuilder)
            randomUserListViewModel.randomUsers.await()

            Mockito.verify(livePagedListBuilder).build()
        }
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel removeUser is called with a RandomUserEntry, " +
                "Then DeleteRandomUserWithIdUseCase is executed with the same RandomUserEntry ID"
    )
    fun removeUser() {
        Mockito.`when`(randomUser.getId()).thenReturn(dummyUserId)
        randomUserListViewModel.removeUser(randomUser)

        Mockito.verify(deleteRandomUserWithIdUseCase).execute(dummyUserId)
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel recoverUser is called with a RandomUserEntry, " +
                "Then RecoverRandomUserUseCase is executed with the same RandomUserEntry"
    )
    fun recoverUser() {
        randomUserListViewModel.recoverUser(randomUser)

        Mockito.verify(recoverRandomUserUseCase).execute(randomUser)
    }

    @Test
    @DisplayName(
        "When RandomUserListViewModel getFilteredRandomUsersBuilder is called with a SearchTerm, " +
                "Then SearchRandomUsersUseCase is executed using that SearchTerm"
    )
    fun getFilteredUsers() {
        Mockito.`when`(searchRandomUsersUseCase.execute("%$searchTerm%")).thenReturn(dataSourceFactory)
        randomUserListViewModel.getFilteredRandomUsersBuilder(searchTerm)

        Mockito.verify(searchRandomUsersUseCase).execute("%$searchTerm%")
        Mockito.verify(livePagedListBuilderFactory).create(
            RandomUsersRepository.PAGES_RANDOM_USERS_SIZE, dataSourceFactory
        )
    }
}
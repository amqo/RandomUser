package com.amqo.randomuser

import android.app.Application
import com.amqo.randomuser.data.db.RandomUsersDatabase
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.domain.*
import com.amqo.randomuser.data.network.*
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.data.repository.RandomUsersRepositoryImpl
import com.amqo.randomuser.ui.base.ResourcesProvider
import com.amqo.randomuser.ui.detail.model.RandomUserDetailActivityViewModelFactory
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModelFactory
import com.amqo.randomuser.ui.list.RandomUserListBoundaryCallback
import com.amqo.randomuser.ui.list.model.LivePagedListBuilderFactory
import com.amqo.randomuser.ui.list.model.RandomUserListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class RandomUserApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@RandomUserApplication))

        // Data base
        bind() from singleton { RandomUsersDatabase(instance()) }
        bind() from singleton { instance<RandomUsersDatabase>().randomUsersDao() }
        // Network
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { RandomUsersApiService(instance()) }
        bind<RandomUsersNetworkDataSource>() with singleton { RandomUsersNetworkDataSourceImpl(instance()) }
        // Repository
        bind<RandomUsersRepository>() with singleton {
            RandomUsersRepositoryImpl(instance(), instance())
        }
        // Use cases
        bind() from singleton { GetNewRandomUsersUseCase(instance()) }
        bind() from singleton { GetLocalRandomUsersUseCase(instance()) }
        bind() from singleton { RecoverRandomUserUseCase(instance()) }
        bind() from singleton { GetRandomUserWithIdUseCase(instance()) }
        bind() from singleton { DeleteRandomUserWithIdUseCase(instance()) }
        bind() from singleton { SearchRandomUsersUseCase(instance()) }
        // Internal
        bind() from singleton { ResourcesProvider(instance()) }
        // View models
        bind() from singleton { RandomUserListBoundaryCallback(instance()) }
        bind() from singleton { LivePagedListBuilderFactory<RandomUserEntry>() }
        bind() from singleton {
            RandomUserListViewModelFactory(instance(), instance(), instance(), instance(), instance(), instance())
        }
        bind() from factory { userId: String ->
            RandomUserDetailFragmentViewModelFactory(
                userId,
                instance(),
                instance()
            )
        }
        bind() from singleton { RandomUserDetailActivityViewModelFactory(instance()) }
    }
}
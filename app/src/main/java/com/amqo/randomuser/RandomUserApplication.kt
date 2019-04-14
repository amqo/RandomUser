package com.amqo.randomuser

import android.app.Application
import com.amqo.randomuser.data.network.*
import com.amqo.randomuser.data.repository.RandomUsersRepository
import com.amqo.randomuser.data.repository.RandomUsersRepositoryImpl
import com.amqo.randomuser.db.RandomUsersDatabase
import com.amqo.randomuser.ui.detail.RandomUserViewModelFactory
import com.amqo.randomuser.ui.list.RandomUserListViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
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

        bind() from singleton { RandomUsersDatabase(instance()) }
        bind() from singleton { instance<RandomUsersDatabase>().randomUsersDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { RandomUsersApiService(instance()) }
        bind<RandomUsersNetworkDataSource>() with singleton { RandomUsersNetworkDataSourceImpl(instance()) }
        bind<RandomUsersRepository>() with singleton {
            RandomUsersRepositoryImpl(instance(), instance())
        }
        bind() from singleton { RandomUserListViewModelFactory(instance()) }
        bind() from factory { userId: String -> RandomUserViewModelFactory(userId, instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
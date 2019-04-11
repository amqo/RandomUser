package com.amqo.randomuser.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amqo.randomuser.data.network.response.RandomUsersResponse
import com.amqo.randomuser.internal.NoConnectivityException

class RandomUsersNetworkDataSourceImpl(
    private val randomUsersApiService: RandomUsersApiService
) : RandomUsersNetworkDataSource {

    private val downloadedRandomUsersMutable = MutableLiveData<RandomUsersResponse>()
    override val downloadedRandomUsers: LiveData<RandomUsersResponse>
        get() = downloadedRandomUsersMutable

    override suspend fun fetchRandomUsers(number: Int) {
        try {
            randomUsersApiService.getRandomUsersAsync(number).await().also {
                downloadedRandomUsersMutable.postValue(it)
            }
        } catch (exception: NoConnectivityException) {
            Log.e(NoConnectivityException::class.java.simpleName, "No internet connection", exception)
        }
    }

}
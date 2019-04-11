package com.amqo.randomuser.data.network

import com.amqo.randomuser.data.network.response.RandomUsersResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.randomuser.me/"

interface RandomUsersApiService {

    companion object {

        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): RandomUsersApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor).build()

            return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RandomUsersApiService::class.java)
        }
    }

    // Example: https://api.randomuser.me/?results=40
    @GET(".")
    fun getRandomUsersAsync(
        @Query("results") results: Int
    ): Deferred<RandomUsersResponse>
}
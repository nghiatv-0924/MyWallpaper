package com.sun.mywallpaper.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    fun <T> buildApi(
        baseUrl: String,
        restApi: Class<T>,
        converterFactory: GsonConverterFactory,
        callAdapterFactory: CoroutineCallAdapterFactory
    ): T =
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(restApi)

    fun <T> buildApi(
        baseUrl: String,
        restApi: Class<T>,
        client: OkHttpClient,
        converterFactory: GsonConverterFactory,
        callAdapterFactory: CoroutineCallAdapterFactory
    ): T =
        Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(restApi)
}

package com.sun.mywallpaper.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sun.mywallpaper.data.api.*
import com.sun.mywallpaper.util.Constants
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single(named(KoinNames.AUTHORIZE_API)) {
        ApiFactory.buildApi(
            baseUrl = get(named(KoinNames.UNSPLASH_URL)),
            restApi = AuthorizeApi::class.java,
            converterFactory = get(named(KoinNames.GSON_CONVERTER_FACTORY)),
            callAdapterFactory = get(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY))
        )
    }

    single(named(KoinNames.COLLECTION_API)) {
        ApiFactory.buildApi(
            baseUrl = get(named(KoinNames.UNSPLASH_API_BASE_URL)),
            restApi = CollectionApi::class.java,
            client = get(named(KoinNames.CLIENT)),
            converterFactory = get(named(KoinNames.GSON_CONVERTER_FACTORY_WITH_DATE_FORMAT)),
            callAdapterFactory = get(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY))
        )
    }

    single(named(KoinNames.PHOTO_API)) {
        ApiFactory.buildApi(
            baseUrl = get(named(KoinNames.UNSPLASH_API_BASE_URL)),
            restApi = PhotoApi::class.java,
            client = get(named(KoinNames.CLIENT)),
            converterFactory = get(named(KoinNames.GSON_CONVERTER_FACTORY_WITH_DATE_FORMAT)),
            callAdapterFactory = get(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY))
        )
    }

    single(named(KoinNames.SEARCH_API)) {
        ApiFactory.buildApi(
            baseUrl = get(named(KoinNames.UNSPLASH_API_BASE_URL)),
            restApi = SearchApi::class.java,
            client = get(named(KoinNames.CLIENT)),
            converterFactory = get(named(KoinNames.GSON_CONVERTER_FACTORY_WITH_DATE_FORMAT)),
            callAdapterFactory = get(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY))
        )
    }

    single(named(KoinNames.USER_API)) {
        ApiFactory.buildApi(
            baseUrl = get(named(KoinNames.UNSPLASH_API_BASE_URL)),
            restApi = UserApi::class.java,
            client = get(named(KoinNames.CLIENT)),
            converterFactory = get(named(KoinNames.GSON_CONVERTER_FACTORY_WITH_DATE_FORMAT)),
            callAdapterFactory = get(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY))
        )
    }

    single(named(KoinNames.CLIENT)) {
        OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
    }

    single(named(KoinNames.COROUTINE_CALL_ADAPTER_FACTORY)) {
        CoroutineCallAdapterFactory()
    }

    single(named(KoinNames.GSON_CONVERTER_FACTORY)) {
        GsonConverterFactory.create()
    }

    single(named(KoinNames.GSON_CONVERTER_FACTORY_WITH_DATE_FORMAT)) {
        GsonConverterFactory.create(get(named(KoinNames.GSON_BUILDER)))
    }


    single(named(KoinNames.GSON_BUILDER)) {
        GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create()
    }

    single(named(KoinNames.UNSPLASH_URL)) {
        ApiConstants.UNSPLASH_URL
    }

    single(named(KoinNames.UNSPLASH_API_BASE_URL)) {
        ApiConstants.UNSPLASH_API_BASE_URL
    }
}

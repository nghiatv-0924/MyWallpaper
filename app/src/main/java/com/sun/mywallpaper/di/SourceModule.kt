package com.sun.mywallpaper.di

import com.sun.mywallpaper.data.database.AppDatabase
import com.sun.mywallpaper.data.repository.*
import com.sun.mywallpaper.data.source.local.WallpaperLocalDataSource
import com.sun.mywallpaper.data.source.remote.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sourceModule = module {

    single(named(KoinNames.AUTHORIZE_REPOSITORY)) {
        AuthorizeRepository(remote = get(named(KoinNames.AUTHORIZE_REMOTE_DATA_SOURCE)))
    }

    single(named(KoinNames.COLLECTION_REPOSITORY)) {
        CollectionRepository(remote = get(named(KoinNames.COLLECTION_REMOTE_DATA_SOURCE)))
    }

    single(named(KoinNames.PHOTO_REPOSITORY)) {
        PhotoRepository(remote = get(named(KoinNames.PHOTO_REMOTE_DATA_SOURCE)))
    }

    single(named(KoinNames.SEARCH_REPOSITORY)) {
        SearchRepository(remote = get(named(KoinNames.SEARCH_REMOTE_DATA_SOURCE)))
    }

    single(named(KoinNames.USER_REPOSITORY)) {
        UserRepository(remote = get(named(KoinNames.USER_REMOTE_DATA_SOURCE)))
    }

    single(named(KoinNames.WALLPAPER_REPOSITORY)) {
        WallpaperRepository(local = get(named(KoinNames.WALLPAPER_LOCAL_DATA_SOURCE)))
    }

    single(named(KoinNames.AUTHORIZE_REMOTE_DATA_SOURCE)) {
        AuthorizeRemoteDataSource(authorizeApi = get(named(KoinNames.AUTHORIZE_API)))
    }

    single(named(KoinNames.COLLECTION_REMOTE_DATA_SOURCE)) {
        CollectionRemoteDataSource(collectionApi = get(named(KoinNames.COLLECTION_API)))
    }

    single(named(KoinNames.PHOTO_REMOTE_DATA_SOURCE)) {
        PhotoRemoteDataSource(photoApi = get(named(KoinNames.PHOTO_API)))
    }

    single(named(KoinNames.SEARCH_REMOTE_DATA_SOURCE)) {
        SearchRemoteDataSource(searchApi = get(named(KoinNames.SEARCH_API)))
    }

    single(named(KoinNames.USER_REMOTE_DATA_SOURCE)) {
        UserRemoteDataSource(userApi = get(named(KoinNames.USER_API)))
    }

    single(named(KoinNames.WALLPAPER_LOCAL_DATA_SOURCE)) {
        WallpaperLocalDataSource(appDatabase = get(named(KoinNames.APP_DATABASE)))
    }

    single(named(KoinNames.APP_DATABASE)) {
        AppDatabase.getInstance(androidContext())
    }
}

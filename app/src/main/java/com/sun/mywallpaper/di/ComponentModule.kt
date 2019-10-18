package com.sun.mywallpaper.di

import com.sun.mywallpaper.adapter.CollectionAdapter
import com.sun.mywallpaper.adapter.PhotoAdapter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val componentModule = module {

    single(named(KoinNames.NEW_ADAPTER)) {
        PhotoAdapter()
    }

    single(named(KoinNames.FEATURED_ADAPTER)) {
        PhotoAdapter()
    }

    single(named(KoinNames.COLLECTION_ADAPTER)) {
        CollectionAdapter()
    }

    single(named(KoinNames.COLLECTION_DETAIL_ADAPTER)) {
        PhotoAdapter()
    }
}

package com.sun.mywallpaper.di

import com.sun.mywallpaper.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }

    viewModel { PhotoViewModel(photoRepository = get(named(KoinNames.PHOTO_REPOSITORY))) }

    viewModel { CollectionViewModel(collectionRepository = get(named(KoinNames.COLLECTION_REPOSITORY))) }

    viewModel { UserViewModel(userRepository = get(named(KoinNames.USER_REPOSITORY))) }

    viewModel { SearchViewModel(searchRepository = get(named(KoinNames.SEARCH_REPOSITORY))) }
}

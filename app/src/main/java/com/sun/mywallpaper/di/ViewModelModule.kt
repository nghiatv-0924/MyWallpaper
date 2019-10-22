package com.sun.mywallpaper.di

import com.sun.mywallpaper.ui.collectiondetail.CollectionDetailViewModel
import com.sun.mywallpaper.ui.home.HomeViewModel
import com.sun.mywallpaper.ui.home.collection.CollectionViewModel
import com.sun.mywallpaper.ui.home.featuredphoto.FeaturedViewModel
import com.sun.mywallpaper.ui.home.newphoto.NewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }

    viewModel { NewViewModel(photoRepository = get(named(KoinNames.PHOTO_REPOSITORY))) }

    viewModel { FeaturedViewModel(photoRepository = get(named(KoinNames.PHOTO_REPOSITORY))) }

    viewModel { CollectionViewModel(collectionRepository = get(named(KoinNames.COLLECTION_REPOSITORY))) }

    viewModel { CollectionDetailViewModel(photoRepository = get(named(KoinNames.PHOTO_REPOSITORY))) }
}

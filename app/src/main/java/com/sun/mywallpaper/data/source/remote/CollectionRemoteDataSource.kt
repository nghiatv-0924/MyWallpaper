package com.sun.mywallpaper.data.source.remote

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.coroutine.DeferredExtensions.awaitResult
import com.sun.mywallpaper.data.api.CollectionApi
import com.sun.mywallpaper.data.api.response.ChangeCollectionPhotoResponse
import com.sun.mywallpaper.data.api.response.DeleteCollectionResponse
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.source.CollectionDataSource

class CollectionRemoteDataSource(private val collectionApi: CollectionApi) :
    CollectionDataSource.Remote {

    override suspend fun getAllCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> =
        collectionApi.getAllCollectionsAsync(page, perPage).awaitResult()

    override suspend fun getCuratedCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> =
        collectionApi.getCuratedCollectionsAsync(page, perPage).awaitResult()

    override suspend fun getFeaturedCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> =
        collectionApi.getFeaturedCollectionsAsync(page, perPage).awaitResult()

    override suspend fun getRelatedCollections(id: String): CoroutineResult<List<Collection>> =
        collectionApi.getRelatedCollectionsAsync(id).awaitResult()

    override suspend fun getUserCollections(
        username: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> =
        collectionApi.getUserCollectionsAsync(username, page, perPage).awaitResult()

    override suspend fun getCollection(id: String): CoroutineResult<Collection> =
        collectionApi.getCollectionAsync(id).awaitResult()

    override suspend fun createCollection(
        title: String,
        description: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> =
        collectionApi.createCollectionAsync(title, description, isPrivate).awaitResult()

    override suspend fun createCollection(
        title: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> =
        collectionApi.createCollectionAsync(title, isPrivate).awaitResult()

    override suspend fun updateCollection(
        id: Int,
        title: String,
        description: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> =
        collectionApi.updateCollectionAsync(id, title, description, isPrivate).awaitResult()

    override suspend fun deleteCollection(id: Int): CoroutineResult<DeleteCollectionResponse> =
        collectionApi.deleteCollectionAsync(id).awaitResult()

    override suspend fun addPhotoToCollection(
        collectionId: Int,
        photoId: String
    ): CoroutineResult<ChangeCollectionPhotoResponse> =
        collectionApi.addPhotoToCollectionAsync(collectionId, photoId).awaitResult()

    override suspend fun deletePhotoFromCollection(
        collectionId: Int,
        photoId: String
    ): CoroutineResult<ChangeCollectionPhotoResponse> =
        collectionApi.deletePhotoFromCollectionAsync(collectionId, photoId).awaitResult()
}

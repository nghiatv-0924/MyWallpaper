package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.ChangeCollectionPhotoResponse
import com.sun.mywallpaper.data.api.response.DeleteCollectionResponse
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.source.CollectionDataSource
import com.sun.mywallpaper.data.source.remote.CollectionRemoteDataSource

class CollectionRepository(
    private val remote: CollectionRemoteDataSource
) : CollectionDataSource.Remote {

    override suspend fun getAllCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> = remote.getAllCollections(page, perPage)

    override suspend fun getCuratedCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> = remote.getCuratedCollections(page, perPage)

    override suspend fun getFeaturedCollections(
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> = remote.getFeaturedCollections(page, perPage)

    override suspend fun getRelatedCollections(id: String): CoroutineResult<List<Collection>> =
        remote.getRelatedCollections(id)

    override suspend fun getUserCollections(
        username: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Collection>> = remote.getUserCollections(username, page, perPage)

    override suspend fun getCollection(id: String): CoroutineResult<Collection> =
        remote.getCollection(id)

    override suspend fun createCollection(
        title: String,
        description: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> = remote.createCollection(title, description, isPrivate)

    override suspend fun createCollection(
        title: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> = remote.createCollection(title, isPrivate)

    override suspend fun updateCollection(
        id: Int,
        title: String,
        description: String,
        isPrivate: Boolean
    ): CoroutineResult<Collection> = remote.updateCollection(id, title, description, isPrivate)

    override suspend fun deleteCollection(id: Int): CoroutineResult<DeleteCollectionResponse> =
        remote.deleteCollection(id)

    override suspend fun addPhotoToCollection(
        collectionId: Int,
        photoId: String
    ): CoroutineResult<ChangeCollectionPhotoResponse> =
        remote.addPhotoToCollection(collectionId, photoId)

    override suspend fun deletePhotoFromCollection(
        collectionId: Int,
        photoId: String
    ): CoroutineResult<ChangeCollectionPhotoResponse> =
        remote.deletePhotoFromCollection(collectionId, photoId)
}

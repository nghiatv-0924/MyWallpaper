package com.sun.mywallpaper.data.source

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.ChangeCollectionPhotoResponse
import com.sun.mywallpaper.data.api.response.DeleteCollectionResponse
import com.sun.mywallpaper.data.model.Collection

interface CollectionDataSource {

    interface Remote {

        suspend fun getAllCollections(page: Int, perPage: Int): CoroutineResult<List<Collection>>

        suspend fun getCuratedCollections(
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Collection>>

        suspend fun getFeaturedCollections(
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Collection>>

        suspend fun getRelatedCollections(id: String): CoroutineResult<List<Collection>>

        suspend fun getUserCollections(
            username: String,
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Collection>>

        suspend fun getCollection(id: String): CoroutineResult<Collection>

        suspend fun createCollection(
            title: String,
            description: String,
            isPrivate: Boolean
        ): CoroutineResult<Collection>

        suspend fun createCollection(title: String, isPrivate: Boolean): CoroutineResult<Collection>

        suspend fun updateCollection(
            id: Int,
            title: String,
            description: String,
            isPrivate: Boolean
        ): CoroutineResult<Collection>

        suspend fun deleteCollection(id: Int): CoroutineResult<DeleteCollectionResponse>

        suspend fun addPhotoToCollection(
            collectionId: Int,
            photoId: String
        ): CoroutineResult<ChangeCollectionPhotoResponse>

        suspend fun deletePhotoFromCollection(
            collectionId: Int,
            photoId: String
        ): CoroutineResult<ChangeCollectionPhotoResponse>
    }
}

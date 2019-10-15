package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.data.api.response.ChangeCollectionPhotoResponse
import com.sun.mywallpaper.data.api.response.DeleteCollectionResponse
import com.sun.mywallpaper.data.model.Collection
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface CollectionApi {

    @GET(PATH_COLLECTIONS)
    fun getAllCollectionsAsync(
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Collection>>

    @GET("$PATH_COLLECTIONS/$PATH_CURATED")
    fun getCuratedCollectionsAsync(
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Collection>>

    @GET("$PATH_COLLECTIONS/$PATH_FEATURED")
    fun getFeaturedCollectionsAsync(
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Collection>>

    @GET("$PATH_COLLECTIONS/{$PATH_ID}/$PATH_RELATED")
    fun getRelatedCollectionsAsync(@Path(PATH_ID) id: String): Deferred<List<Collection>>

    @GET("$PATH_USERS/{$PATH_USERNAME}/$PATH_COLLECTIONS")
    fun getUserCollectionsAsync(
        @Path(PATH_USERNAME) username: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Collection>>

    @GET("$PATH_COLLECTIONS/{$PATH_ID}")
    fun getCollectionAsync(@Path(PATH_ID) id: String): Deferred<Collection>

    @POST(PATH_COLLECTIONS)
    fun createCollectionAsync(
        @Query(QUERY_TITLE) title: String,
        @Query(QUERY_DESCRIPTION) description: String,
        @Query(QUERY_PRIVATE) private: Boolean
    ): Deferred<Collection>

    @POST(PATH_COLLECTIONS)
    fun createCollectionAsync(
        @Query(QUERY_TITLE) title: String,
        @Query(QUERY_PRIVATE) private: Boolean
    ): Deferred<Collection>

    @PUT("$PATH_COLLECTIONS/{$PATH_ID}")
    fun updateCollectionAsync(
        @Path(PATH_ID) id: Int,
        @Query(QUERY_TITLE) title: String,
        @Query(QUERY_DESCRIPTION) description: String,
        @Query(QUERY_PRIVATE) isPrivate: Boolean
    ): Deferred<Collection>

    @DELETE("$PATH_COLLECTIONS/{$PATH_ID}")
    fun deleteCollectionAsync(@Path(PATH_ID) id: Int): Deferred<DeleteCollectionResponse>

    @POST("$PATH_COLLECTIONS/{$PATH_COLLECTION_ID}/$PATH_ADD")
    fun addPhotoToCollectionAsync(
        @Path(PATH_COLLECTION_ID) collectionId: Int,
        @Query(QUERY_PHOTO_ID) photoId: String
    ): Deferred<ChangeCollectionPhotoResponse>

    @DELETE("$PATH_COLLECTIONS/{$PATH_COLLECTION_ID}/$PATH_REMOVE")
    fun deletePhotoFromCollectionAsync(
        @Path(PATH_COLLECTION_ID) collectionId: Int,
        @Query(QUERY_PHOTO_ID) photoId: String
    ): Deferred<ChangeCollectionPhotoResponse>
}

package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.data.api.response.LikePhotoResponse
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.model.PhotoStats
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*

interface PhotoApi {

    @GET(PATH_PHOTOS)
    fun getPhotosAsync(
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int,
        @Query(QUERY_ORDER_BY) orderBy: String
    ): Deferred<List<Photo>>

    @GET("$PATH_PHOTOS/$PATH_CURATED")
    fun getCuratedPhotosAsync(
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int,
        @Query(QUERY_ORDER_BY) orderBy: String
    ): Deferred<List<Photo>>

    @GET("$PATH_PHOTOS/{$PATH_ID}/$PATH_STATISTICS")
    fun getPhotoStatsAsync(
        @Path(PATH_ID) id: String,
        @Query(QUERY_RESOLUTION) resolution: String,
        @Query(QUERY_QUANTITY) quantity: Int
    ): Deferred<PhotoStats>

    @GET("$PATH_CATEGORIES/{$PATH_ID}/$PATH_PHOTOS")
    fun getPhotosInAGivenCategoryAsync(
        @Path(PATH_ID) id: Int,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Photo>>

    @POST("$PATH_PHOTOS/{$PATH_ID}/$PATH_LIKE")
    fun likeAPhotoAsync(@Path(PATH_ID) id: String): Deferred<LikePhotoResponse>

    @DELETE("$PATH_PHOTOS/{$PATH_ID}/$PATH_LIKE")
    fun unlikeAPhotoAsync(@Path(PATH_ID) id: String): Deferred<LikePhotoResponse>

    @GET("$PATH_PHOTOS/{$PATH_ID}")
    fun getAPhotoAsync(@Path(PATH_ID) id: String): Deferred<Photo>

    @GET("$PATH_USERS/{$PATH_USERNAME}/$PATH_PHOTOS")
    fun getUserPhotosAsync(
        @Path(PATH_USERNAME) username: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int,
        @Query(QUERY_ORDER_BY) orderBy: String
    ): Deferred<List<Photo>>

    @GET("$PATH_USERS/{$PATH_USERNAME}/$PATH_LIKE")
    fun getUserLikesAsync(
        @Path(PATH_USERNAME) username: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int,
        @Query(QUERY_ORDER_BY) orderBy: String
    ): Deferred<List<Photo>>

    @GET("$PATH_COLLECTIONS/{$PATH_ID}/$PATH_PHOTOS")
    fun getCollectionPhotosAsync(
        @Path(PATH_ID) id: Int,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Photo>>

    @GET("$PATH_COLLECTIONS/$PATH_CURATED/{$PATH_ID}/$PATH_PHOTOS")
    fun getCuratedCollectionPhotosAsync(
        @Path(PATH_ID) id: Int,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<List<Photo>>

    @GET("$PATH_PHOTOS/$PATH_RANDOM")
    fun getRandomPhotosAsync(
        @Query(QUERY_COLLECTIONS) collectionsId: Int,
        @Query(QUERY_FEATURED) featured: Boolean,
        @Query(QUERY_USERNAME) username: String,
        @Query(QUERY_QUERY) query: String,
        @Query(QUERY_ORIENTATION) orientation: String,
        @Query(QUERY_COUNT) count: Int
    ): Deferred<List<Photo>>

    @GET("$PATH_PHOTOS/{$PATH_ID}/$PATH_DOWNLOAD")
    fun reportDownloadAsync(@Path(PATH_ID) id: String): Deferred<ResponseBody>
}

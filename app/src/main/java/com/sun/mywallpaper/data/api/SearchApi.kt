package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.data.api.response.SearchCollectionsResponse
import com.sun.mywallpaper.data.api.response.SearchPhotosResponse
import com.sun.mywallpaper.data.api.response.SearchUsersResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("$PATH_SEARCH/$PATH_PHOTOS")
    fun searchPhotos(
        @Query(QUERY_QUERY) query: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int,
        @Query(QUERY_COLLECTIONS) collections: String,
        @Query(QUERY_ORIENTATION) orientation: String
    ): Deferred<SearchPhotosResponse>

    @GET("$PATH_SEARCH/$PATH_USERS")
    fun searchUsers(
        @Query(QUERY_QUERY) query: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<SearchUsersResponse>

    @GET("$PATH_SEARCH/$PATH_COLLECTIONS")
    fun searchCollections(
        @Query(QUERY_QUERY) query: String,
        @Query(QUERY_PAGE) page: Int,
        @Query(QUERY_PER_PAGE) perPage: Int
    ): Deferred<SearchCollectionsResponse>
}

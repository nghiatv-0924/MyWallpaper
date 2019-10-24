package com.sun.mywallpaper.data.source.remote

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.coroutine.DeferredExtensions.awaitResult
import com.sun.mywallpaper.data.api.SearchApi
import com.sun.mywallpaper.data.api.response.SearchCollectionsResponse
import com.sun.mywallpaper.data.api.response.SearchPhotosResponse
import com.sun.mywallpaper.data.api.response.SearchUsersResponse
import com.sun.mywallpaper.data.source.SearchDataSource

class SearchRemoteDataSource(private val searchApi: SearchApi) : SearchDataSource.Remote {

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        collections: String?,
        orientation: String?
    ): CoroutineResult<SearchPhotosResponse> =
        searchApi.searchPhotosAsync(query, page, perPage, collections, orientation).awaitResult()

    override suspend fun searchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<SearchUsersResponse> =
        searchApi.searchUsersAsync(query, page, perPage).awaitResult()

    override suspend fun searchCollections(
        query: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<SearchCollectionsResponse> =
        searchApi.searchCollectionsAsync(query, page, perPage).awaitResult()
}

package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.SearchCollectionsResponse
import com.sun.mywallpaper.data.api.response.SearchPhotosResponse
import com.sun.mywallpaper.data.api.response.SearchUsersResponse
import com.sun.mywallpaper.data.source.SearchDataSource
import com.sun.mywallpaper.data.source.remote.SearchRemoteDataSource

class SearchRepository(private val remote: SearchRemoteDataSource) : SearchDataSource.Remote {

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        collections: String?,
        orientation: String?
    ): CoroutineResult<SearchPhotosResponse> =
        remote.searchPhotos(query, page, perPage, collections, orientation)

    override suspend fun searchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<SearchUsersResponse> = remote.searchUsers(query, page, perPage)

    override suspend fun searchCollections(
        query: String,
        page: Int,
        perPage: Int
    ): CoroutineResult<SearchCollectionsResponse> =
        remote.searchCollections(query, page, perPage)
}

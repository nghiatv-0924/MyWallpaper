package com.sun.mywallpaper.data.source

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.SearchCollectionsResponse
import com.sun.mywallpaper.data.api.response.SearchPhotosResponse
import com.sun.mywallpaper.data.api.response.SearchUsersResponse

interface SearchDataSource {

    interface Remote {
        suspend fun searchPhotos(
            query: String,
            page: Int,
            perPage: Int,
            collections: String,
            orientation: String
        ): CoroutineResult<SearchPhotosResponse>

        suspend fun searchUsers(
            query: String,
            page: Int,
            perPage: Int
        ): CoroutineResult<SearchUsersResponse>

        suspend fun searchCollections(
            query: String,
            page: Int,
            perPage: Int
        ): CoroutineResult<SearchCollectionsResponse>
    }
}

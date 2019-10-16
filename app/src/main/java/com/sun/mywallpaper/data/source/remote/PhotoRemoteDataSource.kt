package com.sun.mywallpaper.data.source.remote

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.coroutine.DeferredExtensions.awaitResult
import com.sun.mywallpaper.data.api.PhotoApi
import com.sun.mywallpaper.data.api.response.LikePhotoResponse
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.model.PhotoStats
import com.sun.mywallpaper.data.source.PhotoDataSource
import okhttp3.ResponseBody

class PhotoRemoteDataSource(private val photoApi: PhotoApi) : PhotoDataSource.Remote {

    override suspend fun getPhotos(
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> =
        photoApi.getPhotosAsync(page, perPage, orderBy).awaitResult()

    override suspend fun getCuratedPhotos(
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> =
        photoApi.getCuratedPhotosAsync(page, perPage, orderBy).awaitResult()

    override suspend fun getPhotoStats(
        id: String,
        resolution: String,
        quantity: Int
    ): CoroutineResult<PhotoStats> =
        photoApi.getPhotoStatsAsync(id, resolution, quantity).awaitResult()

    override suspend fun getPhotosInAGivenCategory(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> =
        photoApi.getPhotosInAGivenCategoryAsync(id, page, perPage).awaitResult()

    override suspend fun likeAPhoto(id: String): CoroutineResult<LikePhotoResponse> =
        photoApi.likeAPhotoAsync(id).awaitResult()

    override suspend fun unlikeAPhoto(id: String): CoroutineResult<LikePhotoResponse> =
        photoApi.unlikeAPhotoAsync(id).awaitResult()

    override suspend fun getAPhoto(id: String): CoroutineResult<Photo> =
        photoApi.getAPhotoAsync(id).awaitResult()

    override suspend fun getUserPhotos(
        username: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> =
        photoApi.getUserPhotosAsync(username, page, perPage, orderBy).awaitResult()

    override suspend fun getUserLikes(
        username: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> =
        photoApi.getUserLikesAsync(username, page, perPage, orderBy).awaitResult()

    override suspend fun getCollectionPhotos(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> =
        photoApi.getCollectionPhotosAsync(id, page, perPage).awaitResult()

    override suspend fun getCuratedCollectionPhotos(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> =
        photoApi.getCuratedCollectionPhotosAsync(id, page, perPage).awaitResult()

    override suspend fun getRandomPhotos(
        collectionsId: Int,
        featured: Boolean,
        username: String,
        query: String,
        orientation: String,
        count: Int
    ): CoroutineResult<List<Photo>> =
        photoApi.getRandomPhotosAsync(collectionsId, featured, username, query, orientation, count)
            .awaitResult()

    override suspend fun reportDownload(id: String): CoroutineResult<ResponseBody> =
        photoApi.reportDownloadAsync(id).awaitResult()
}

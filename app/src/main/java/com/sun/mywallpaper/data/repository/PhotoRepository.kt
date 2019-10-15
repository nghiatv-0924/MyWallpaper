package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.LikePhotoResponse
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.model.PhotoStats
import com.sun.mywallpaper.data.source.PhotoDataSource
import com.sun.mywallpaper.data.source.remote.PhotoRemoteDataSource
import okhttp3.ResponseBody

class PhotoRepository(private val remote: PhotoRemoteDataSource) : PhotoDataSource.Remote {

    override suspend fun getPhotos(
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> = remote.getPhotos(page, perPage, orderBy)

    override suspend fun getCuratedPhotos(
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> = remote.getCuratedPhotos(page, perPage, orderBy)

    override suspend fun getPhotoStats(
        id: String,
        resolution: String,
        quantity: Int
    ): CoroutineResult<PhotoStats> = remote.getPhotoStats(id, resolution, quantity)

    override suspend fun getPhotosInAGivenCategory(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> = remote.getPhotosInAGivenCategory(id, page, perPage)

    override suspend fun likeAPhoto(id: String): CoroutineResult<LikePhotoResponse> =
        remote.likeAPhoto(id)

    override suspend fun unlikeAPhoto(id: String): CoroutineResult<LikePhotoResponse> =
        remote.unlikeAPhoto(id)

    override suspend fun getAPhoto(id: String): CoroutineResult<Photo> = remote.getAPhoto(id)

    override suspend fun getUserPhotos(
        username: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> = remote.getUserPhotos(username, page, perPage, orderBy)

    override suspend fun getUserLikes(
        username: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): CoroutineResult<List<Photo>> = remote.getUserLikes(username, page, perPage, orderBy)

    override suspend fun getCollectionPhotos(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> = remote.getCollectionPhotos(id, page, perPage)

    override suspend fun getCuratedCollectionPhotos(
        id: Int,
        page: Int,
        perPage: Int
    ): CoroutineResult<List<Photo>> = remote.getCuratedCollectionPhotos(id, page, perPage)

    override suspend fun getRandomPhotos(
        collectionsId: Int,
        featured: Boolean,
        username: String,
        query: String,
        orientation: String,
        count: Int
    ): CoroutineResult<List<Photo>> =
        remote.getRandomPhotos(collectionsId, featured, username, query, orientation, count)

    override suspend fun reportDownload(id: String): CoroutineResult<ResponseBody> =
        remote.reportDownload(id)
}

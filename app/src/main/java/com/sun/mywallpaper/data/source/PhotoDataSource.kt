package com.sun.mywallpaper.data.source

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Create repository
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.LikePhotoResponse
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.model.PhotoStats
import okhttp3.ResponseBody

<<<<<<< HEAD
=======
>>>>>>> Create repository
=======
>>>>>>> Create repository
interface PhotoDataSource {

    interface Remote {

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Create repository
        suspend fun getPhotos(
            page: Int,
            perPage: Int,
            orderBy: String
        ): CoroutineResult<List<Photo>>

        suspend fun getCuratedPhotos(
            page: Int,
            perPage: Int,
            orderBy: String
        ): CoroutineResult<List<Photo>>

        suspend fun getPhotoStats(
            id: String,
            resolution: String,
            quantity: Int
        ): CoroutineResult<PhotoStats>

        suspend fun getPhotosInAGivenCategory(
            id: Int,
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Photo>>

        suspend fun likeAPhoto(id: String): CoroutineResult<LikePhotoResponse>

        suspend fun unlikeAPhoto(id: String): CoroutineResult<LikePhotoResponse>

        suspend fun getAPhoto(id: String): CoroutineResult<Photo>

        suspend fun getUserPhotos(
            username: String,
            page: Int,
            perPage: Int,
            orderBy: String
        ): CoroutineResult<List<Photo>>

        suspend fun getUserLikes(
            username: String,
            page: Int,
            perPage: Int,
            orderBy: String
        ): CoroutineResult<List<Photo>>

        suspend fun getCollectionPhotos(
            id: Int,
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Photo>>

        suspend fun getCuratedCollectionPhotos(
            id: Int,
            page: Int,
            perPage: Int
        ): CoroutineResult<List<Photo>>

        suspend fun getRandomPhotos(
            collectionsId: Int,
            featured: Boolean,
            username: String,
            query: String,
            orientation: String,
            count: Int
        ): CoroutineResult<List<Photo>>

        suspend fun reportDownload(id: String): CoroutineResult<ResponseBody>
<<<<<<< HEAD
=======
        fun getPhotos() : CoroutineResult
>>>>>>> Create repository
=======
>>>>>>> Create repository
    }
}

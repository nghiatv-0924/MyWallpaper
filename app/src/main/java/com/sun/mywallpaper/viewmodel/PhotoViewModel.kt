package com.sun.mywallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class PhotoViewModel(private val photoRepository: PhotoRepository) : BaseViewModel() {

    val newPhotos: LiveData<List<Photo>>
        get() = _newPhotos
    val featuredPhotos: LiveData<List<Photo>>
        get() = _featuredPhotos
    val collectionPhotos: LiveData<List<Photo>>
        get() = _collectionPhotos
    val userPhotos: LiveData<List<Photo>>
        get() = _userPhotos
    val userLikes: LiveData<List<Photo>>
        get() = _userLikes

    private val _newPhotos = MutableLiveData<List<Photo>>()
    private val _featuredPhotos = MutableLiveData<List<Photo>>()
    private val _collectionPhotos = MutableLiveData<List<Photo>>()
    private val _userPhotos = MutableLiveData<List<Photo>>()
    private val _userLikes = MutableLiveData<List<Photo>>()

    private val newPhotoList = mutableListOf<Photo>()
    private val featuredPhotoList = mutableListOf<Photo>()
    private val collectionPhotoList = mutableListOf<Photo>()
    private val userPhotoList = mutableListOf<Photo>()
    private val userLikeList = mutableListOf<Photo>()

    override fun create() {
    }

    fun refreshNewPhotos(page: Int, perPage: Int, orderBy: String) {
        newPhotoList.clear()
        getNewPhotos(page, perPage, orderBy)
    }

    fun getNewPhotos(page: Int, perPage: Int, orderBy: String) = launch {
        when (val result = photoRepository.getPhotos(page, perPage, orderBy)) {
            is CoroutineResult.Success -> newPhotoList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                newPhotoList.addAll(emptyList())
            }
        }
        _newPhotos.value = newPhotoList
    }

    fun refreshFeaturedPhotos(page: Int, perPage: Int, orderBy: String) {
        featuredPhotoList.clear()
        getFeaturedPhotos(page, perPage, orderBy)
    }

    fun getFeaturedPhotos(page: Int, perPage: Int, orderBy: String) = launch {
        when (val result = photoRepository.getCuratedPhotos(page, perPage, orderBy)) {
            is CoroutineResult.Success -> featuredPhotoList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                featuredPhotoList.addAll(emptyList())
            }
        }
        _featuredPhotos.value = featuredPhotoList
    }

    fun refreshCollectionPhotos(id: Int, page: Int, perPage: Int) {
        collectionPhotoList.clear()
        getCollectionPhotos(id, page, perPage)
    }

    fun getCollectionPhotos(id: Int, page: Int, perPage: Int) = launch {
        when (val result = photoRepository.getCollectionPhotos(id, page, perPage)) {
            is CoroutineResult.Success -> collectionPhotoList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                collectionPhotoList.addAll(emptyList())
            }
        }
        _collectionPhotos.value = collectionPhotoList
    }

    fun refreshUserPhotos(userName: String, page: Int, perPage: Int, orderBy: String) {
        userPhotoList.clear()
        getUserPhotos(userName, page, perPage, orderBy)
    }

    fun getUserPhotos(userName: String, page: Int, perPage: Int, orderBy: String) = launch {
        when (val result = photoRepository.getUserPhotos(userName, page, perPage, orderBy)) {
            is CoroutineResult.Success -> userPhotoList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                userPhotoList.addAll(emptyList())
            }
        }
        _userPhotos.value = userPhotoList
    }

    fun refreshUserLikes(userName: String, page: Int, perPage: Int, orderBy: String) {
        userLikeList.clear()
        getUserLikes(userName, page, perPage, orderBy)
    }

    fun getUserLikes(userName: String, page: Int, perPage: Int, orderBy: String) = launch {
        when (val result = photoRepository.getUserLikes(userName, page, perPage, orderBy)) {
            is CoroutineResult.Success -> userLikeList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                userLikeList.addAll(emptyList())
            }
        }
        _userLikes.value = userLikeList
    }
}

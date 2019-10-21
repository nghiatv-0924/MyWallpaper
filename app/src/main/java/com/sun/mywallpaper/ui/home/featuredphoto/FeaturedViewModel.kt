package com.sun.mywallpaper.ui.home.featuredphoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class FeaturedViewModel(private val photoRepository: PhotoRepository) : BaseViewModel() {

    val featuredPhotos: LiveData<List<Photo>>
        get() = _featuredPhotos
    private val _featuredPhotos = MutableLiveData<List<Photo>>()
    private val featuredPhotoList = mutableListOf<Photo>()

    override fun create() {
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
}

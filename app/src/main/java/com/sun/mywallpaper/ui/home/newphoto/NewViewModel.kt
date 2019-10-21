package com.sun.mywallpaper.ui.home.newphoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class NewViewModel(private val photoRepository: PhotoRepository) : BaseViewModel() {

    val newPhotos: LiveData<List<Photo>>
        get() = _newPhotos
    private val _newPhotos = MutableLiveData<List<Photo>>()
    private val newPhotoList = mutableListOf<Photo>()

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
}

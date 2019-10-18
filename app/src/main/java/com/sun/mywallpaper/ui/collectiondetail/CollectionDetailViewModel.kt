package com.sun.mywallpaper.ui.collectiondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Photo
import com.sun.mywallpaper.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private val photoRepository: PhotoRepository
) : BaseViewModel() {

    val photos: LiveData<List<Photo>>
        get() = _photos
    private val _photos = MutableLiveData<List<Photo>>()
    private val photoList = mutableListOf<Photo>()

    override fun create() {
    }

    fun refreshPhotos(id: Int, page: Int, perPage: Int) {
        photoList.clear()
        getPhotos(id, page, perPage)
    }

    fun getPhotos(id: Int, page: Int, perPage: Int) = launch {
        when (val result = photoRepository.getCollectionPhotos(id, page, perPage)) {
            is CoroutineResult.Success -> photoList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                photoList.addAll(emptyList())
            }
        }
        _photos.value = photoList
    }
}

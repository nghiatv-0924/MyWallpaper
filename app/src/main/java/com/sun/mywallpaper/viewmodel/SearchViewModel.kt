package com.sun.mywallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.api.response.SearchCollectionsResponse
import com.sun.mywallpaper.data.api.response.SearchPhotosResponse
import com.sun.mywallpaper.data.api.response.SearchUsersResponse
import com.sun.mywallpaper.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    val searchPhotoResponses: LiveData<SearchPhotosResponse>
        get() = _searchPhotoResponses
    val searchCollectionResponses: LiveData<SearchCollectionsResponse>
        get() = _searchCollectionResponses
    val searchUserResponses: LiveData<SearchUsersResponse>
        get() = _searchUserResponses

    private val _searchPhotoResponses = MutableLiveData<SearchPhotosResponse>()
    private val _searchCollectionResponses = MutableLiveData<SearchCollectionsResponse>()
    private val _searchUserResponses = MutableLiveData<SearchUsersResponse>()

    override fun create() {
    }

    fun getSearchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        collections: String?,
        orientation: String?
    ) = launch {
        when (val result =
            searchRepository.searchPhotos(query, page, perPage, collections, orientation)) {
            is CoroutineResult.Success -> _searchPhotoResponses.value = result.data
            is CoroutineResult.Error -> messageNotification.value =
                result.throwable.message.toString()

        }
    }

    fun getSearchCollections(query: String, page: Int, perPage: Int) = launch {
        when (val result = searchRepository.searchCollections(query, page, perPage)) {
            is CoroutineResult.Success -> _searchCollectionResponses.value = result.data
            is CoroutineResult.Error -> messageNotification.value =
                result.throwable.message.toString()
        }
    }

    fun getSearchUsers(query: String, page: Int, perPage: Int) = launch {
        when (val result = searchRepository.searchUsers(query, page, perPage)) {
            is CoroutineResult.Success -> _searchUserResponses.value = result.data
            is CoroutineResult.Error -> messageNotification.value =
                result.throwable.message.toString()
        }
    }
}

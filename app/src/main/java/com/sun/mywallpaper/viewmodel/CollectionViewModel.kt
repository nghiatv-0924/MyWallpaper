package com.sun.mywallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.repository.CollectionRepository
import com.sun.mywallpaper.ui.home.CollectionFragment
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val collectionRepository: CollectionRepository
) : BaseViewModel() {

    val collections: LiveData<List<Collection>>
        get() = _collections
    val userCollections: LiveData<List<Collection>>
        get() = _userCollections

    private val _collections = MutableLiveData<List<Collection>>()
    private val _userCollections = MutableLiveData<List<Collection>>()

    private val collectionList = mutableListOf<Collection>()
    private val userCollectionList = mutableListOf<Collection>()

    override fun create() {
    }

    fun refreshCollections(page: Int, perPage: Int, orderBy: String) {
        collectionList.clear()
        getCollections(page, perPage, orderBy)
    }

    fun getCollections(page: Int, perPage: Int, orderBy: String) = launch {
        val result = if (orderBy == CollectionFragment.ORDER_BY_ALL)
            collectionRepository.getAllCollections(page, perPage)
        else
            collectionRepository.getFeaturedCollections(page, perPage)

        when (result) {
            is CoroutineResult.Success -> collectionList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                collectionList.addAll(emptyList())
            }
        }
        _collections.value = collectionList
    }

    fun refreshUserCollections(username: String, page: Int, perPage: Int) {
        userCollectionList.clear()
        getUserCollections(username, page, perPage)
    }

    fun getUserCollections(username: String, page: Int, perPage: Int) = launch {
        when (val result = collectionRepository.getUserCollections(username, page, perPage)) {
            is CoroutineResult.Success -> userCollectionList.addAll(result.data)
            is CoroutineResult.Error -> {
                messageNotification.value = result.throwable.message.toString()
                userCollectionList.addAll(emptyList())
            }
        }
        _userCollections.value = userCollectionList
    }
}

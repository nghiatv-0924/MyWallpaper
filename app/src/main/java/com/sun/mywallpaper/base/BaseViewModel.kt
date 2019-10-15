package com.sun.mywallpaper.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent, Observable {

    val messageNotification = MutableLiveData<String>()

    private val callbacks = PropertyChangeRegistry()

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    fun notifyChange() = callbacks.notifyCallbacks(this, 0, null)

    fun notifyPropertyChanged(fieldId: Int) = callbacks.notifyCallbacks(this, fieldId, null)

    abstract fun create()
}

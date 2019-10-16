package com.sun.mywallpaper.coroutine

sealed class CoroutineResult<out T : Any> {

    class Success<out T : Any>(val data: T) : CoroutineResult<T>()

    class Error(val throwable: Throwable) : CoroutineResult<Nothing>()
}

fun <T : Any, DATA> CoroutineResult<T>.getData(
    onSuccess: (resultData: T) -> DATA,
    onFailed: (throwable: Throwable) -> DATA
): DATA = when (this) {

    is CoroutineResult.Success -> onSuccess(this.data)
    is CoroutineResult.Error -> onFailed(this.throwable)
}

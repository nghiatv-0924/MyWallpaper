package com.sun.mywallpaper.coroutine

import com.sun.mywallpaper.util.Constants
import kotlinx.coroutines.*
import kotlin.coroutines.resume

object DeferredExtensions {

    suspend fun <T : Any> Deferred<T>.awaitResult(): CoroutineResult<T> =
        suspendCancellableCoroutine {
            GlobalScope.launch {
                try {
                    val response = await()
                    if (response.isNotAvailable()) {
                        throw Exception(Constants.MESSAGE_DATA_NOT_FOUND)
                    } else {
                        it.resume(CoroutineResult.Success(response))
                    }
                } catch (e: Throwable) {
                    it.resume(CoroutineResult.Error(e))
                }
            }
            registerOnCompletion(it)
        }

    private fun Deferred<*>.registerOnCompletion(continuation: CancellableContinuation<*>) {
        continuation.invokeOnCancellation {
            if (continuation.isCancelled)
                try {
                    cancel()
                } catch (ex: Throwable) {
                    ex.printStackTrace()
                }
        }
    }

    private fun <RESPONSE> RESPONSE.isNotAvailable() =
        this == null || (this is List<*> && isEmpty())
}

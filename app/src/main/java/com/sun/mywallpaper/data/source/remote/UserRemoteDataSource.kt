package com.sun.mywallpaper.data.source.remote

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.coroutine.DeferredExtensions.awaitResult
import com.sun.mywallpaper.data.api.UserApi
import com.sun.mywallpaper.data.model.Me
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.data.source.UserDataSource

class UserRemoteDataSource(private val userApi: UserApi) : UserDataSource.Remote {

    override suspend fun getUserProfile(username: String, w: Int, h: Int): CoroutineResult<User> =
        userApi.getUserProfileAsync(username, w, h).awaitResult()

    override suspend fun getMeProfile(): CoroutineResult<Me> =
        userApi.getMeProfileAsync().awaitResult()

    override suspend fun updateMeProfile(
        username: String,
        first_name: String,
        last_name: String,
        email: String,
        url: String,
        location: String,
        bio: String,
        instagramUsername: String
    ): CoroutineResult<Me> =
        userApi.updateMeProfileAsync(
            username,
            first_name,
            last_name,
            email,
            url,
            location,
            bio,
            instagramUsername
        ).awaitResult()
}

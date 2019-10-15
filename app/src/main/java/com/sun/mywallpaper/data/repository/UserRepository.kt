package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Me
import com.sun.mywallpaper.data.model.User
import com.sun.mywallpaper.data.source.UserDataSource
import com.sun.mywallpaper.data.source.remote.UserRemoteDataSource

class UserRepository(private val remote: UserRemoteDataSource) : UserDataSource.Remote {

    override suspend fun getUserProfile(username: String, w: Int, h: Int): CoroutineResult<User> =
        remote.getUserProfile(username, w, h)

    override suspend fun getMeProfile(): CoroutineResult<Me> = remote.getMeProfile()

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
        remote.updateMeProfile(
            username,
            first_name,
            last_name,
            email,
            url,
            location,
            bio,
            instagramUsername
        )
}

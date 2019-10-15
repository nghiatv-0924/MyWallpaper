package com.sun.mywallpaper.data.source

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Me
import com.sun.mywallpaper.data.model.User

interface UserDataSource {

    interface Remote {

        suspend fun getUserProfile(username: String, w: Int, h: Int): CoroutineResult<User>

        suspend fun getMeProfile(): CoroutineResult<Me>

        suspend fun updateMeProfile(
            username: String,
            first_name: String,
            last_name: String,
            email: String,
            url: String,
            location: String,
            bio: String,
            instagramUsername: String
        ): CoroutineResult<Me>
    }
}

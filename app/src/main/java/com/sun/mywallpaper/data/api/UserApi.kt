package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.data.model.Me
import com.sun.mywallpaper.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("$PATH_USERS/{$PATH_USERNAME}")
    fun getUserProfileAsync(
        @Path(PATH_USERNAME) username: String,
        @Query(QUERY_W) w: Int,
        @Query(QUERY_H) h: Int
    ): Deferred<User>

    @GET(PATH_ME)
    fun getMeProfileAsync(): Deferred<Me>

    @PUT(PATH_ME)
    fun updateMeProfileAsync(
        @Query(QUERY_USERNAME) username: String,
        @Query(QUERY_FIRST_NAME) first_name: String,
        @Query(QUERY_LAST_NAME) last_name: String,
        @Query(QUERY_EMAIL) email: String,
        @Query(QUERY_URL) url: String,
        @Query(QUERY_LOCATION) location: String,
        @Query(QUERY_BIO) bio: String,
        @Query(QUERY_INSTAGRAM_USERNAME) instagramUsername: String
    ): Deferred<Me>
}

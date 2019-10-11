package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.data.model.AccessToken
import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthorizeApi {

    @POST("$PATH_OAUTH/$PATH_TOKEN")
    fun getAccessTokenAsync(
        @Query(QUERY_CLIENT_ID) clientId: String,
        @Query(QUERY_CLIENT_SECRET) clientSecret: String,
        @Query(QUERY_REDIRECT_URI) redirectUri: String,
        @Query(QUERY_CODE) code: String,
        @Query(QUERY_GRANT_TYPE) grantType: String
    ): Deferred<AccessToken>
}

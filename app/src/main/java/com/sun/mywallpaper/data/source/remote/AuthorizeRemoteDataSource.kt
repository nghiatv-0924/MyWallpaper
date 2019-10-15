package com.sun.mywallpaper.data.source.remote

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.coroutine.DeferredExtensions.awaitResult
import com.sun.mywallpaper.data.api.AuthorizeApi
import com.sun.mywallpaper.data.model.AccessToken
import com.sun.mywallpaper.data.source.AuthorizeDataSource

<<<<<<< HEAD
class AuthorizeRemoteDataSource(
    private val authorizeApi: AuthorizeApi
) : AuthorizeDataSource.Remote {
=======
class AuthorizeRemoteDataSource(private val authorizeApi: AuthorizeApi) :
    AuthorizeDataSource.Remote {
>>>>>>> Create repository

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        code: String,
        grantType: String
    ): CoroutineResult<AccessToken> =
        authorizeApi.getAccessTokenAsync(
            clientId,
            clientSecret,
            redirectUri,
            code,
            grantType
        ).awaitResult()
}

package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.AccessToken
import com.sun.mywallpaper.data.source.AuthorizeDataSource
import com.sun.mywallpaper.data.source.remote.AuthorizeRemoteDataSource

class AuthorizeRepository(
    private val remote: AuthorizeRemoteDataSource
) : AuthorizeDataSource.Remote {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        code: String,
        grantType: String
    ): CoroutineResult<AccessToken> =
        remote.getAccessToken(clientId, clientSecret, redirectUri, code, grantType)
}

package com.sun.mywallpaper.data.source

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.AccessToken

interface AuthorizeDataSource {

    interface Remote {
        suspend fun getAccessToken(
            clientId: String,
            clientSecret: String,
            redirectUri: String,
            code: String,
            grantType: String
        ): CoroutineResult<AccessToken>
    }
}

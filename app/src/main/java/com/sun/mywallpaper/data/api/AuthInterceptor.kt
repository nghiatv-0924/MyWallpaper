package com.sun.mywallpaper.data.api

import com.sun.mywallpaper.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (AuthManager.getInstance().isAuthorized()) {
            chain.request()
                .newBuilder()
                .addHeader(
                    NAME_AUTHORIZATION,
                    VALUE_BEARER + AuthManager.getInstance().getAccessToken()
                )
                .build()
        } else {
            chain.request()
                .newBuilder()
                .addHeader(
                    NAME_AUTHORIZATION,
                    VALUE_CLIENT_ID + BuildConfig.API_ACCESS_KEY
                )
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val NAME_AUTHORIZATION = "Authorization"
        private const val VALUE_BEARER = "Bearer "
        private const val VALUE_CLIENT_ID = "Client-ID "
    }
}

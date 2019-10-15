package com.sun.mywallpaper.data.api

class AuthManager {

    fun isAuthorized(): Boolean {
        return true
    }

    fun getAccessToken(): String = ""

    companion object {
        @Volatile
        private var INSTANCE: AuthManager? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AuthManager().also { INSTANCE = it }
        }
    }
}


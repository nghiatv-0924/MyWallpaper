package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName(JSON_KEY_ACCESS_TOKEN) val accessToken: String,
    @SerializedName(JSON_KEY_TOKEN_TYPE) val tokenType: String,
    @SerializedName(JSON_KEY_SCOPE) val scope: String,
    @SerializedName(JSON_KEY_CREATED_AT) val createdAt: Int
) {
    companion object {
        private const val JSON_KEY_ACCESS_TOKEN = "access_token"
        private const val JSON_KEY_TOKEN_TYPE = "token_type"
        private const val JSON_KEY_SCOPE = "scope"
        private const val JSON_KEY_CREATED_AT = "created_at"
    }
}

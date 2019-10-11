package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class PhotoStats(
    @SerializedName(JSON_KEY_ID) val id: String,
    @SerializedName(JSON_KEY_DOWNLOADS_ACTION) val downloads: Action,
    @SerializedName(JSON_KEY_VIEWS_ACTION) val views: Action,
    @SerializedName(JSON_KEY_LIKES_ACTION) val likes: Action
) {
    data class Action(
        @SerializedName(JSON_KEY_ACTION_TOTAL) val total: Int,
        @SerializedName(JSON_KEY_ACTION_HISTORICAL) val historical: Historical
    )

    companion object {
        private const val JSON_KEY_ID = "id"
        private const val JSON_KEY_DOWNLOADS_ACTION = "downloads"
        private const val JSON_KEY_VIEWS_ACTION = "views"
        private const val JSON_KEY_LIKES_ACTION = "likes"
        private const val JSON_KEY_ACTION_TOTAL = "total"
        private const val JSON_KEY_ACTION_HISTORICAL = "historical"
    }
}

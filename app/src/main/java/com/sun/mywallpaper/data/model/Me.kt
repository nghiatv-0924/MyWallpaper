package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class Me(
    @SerializedName(JSON_KEY_ID) val id: String,
    @SerializedName(JSON_KEY_UPDATED_AT) val updatedAt: String,
    @SerializedName(JSON_KEY_USERNAME) val username: String,
    @SerializedName(JSON_KEY_FIRST_NAME) val firstName: String,
    @SerializedName(JSON_KEY_LAST_NAME) val last_name: String,
    @SerializedName(JSON_KEY_TWITTER_USERNAME) val twitterUsername: String,
    @SerializedName(JSON_KEY_PORTFOLIO_URL) val portfolio_url: String,
    @SerializedName(JSON_KEY_BIO) val bio: String,
    @SerializedName(JSON_KEY_LOCATION) val location: String,
    @SerializedName(JSON_KEY_TOTAL_LIKES) val total_likes: Int,
    @SerializedName(JSON_KEY_TOTAL_PHOTOS) val total_photos: Int,
    @SerializedName(JSON_KEY_TOTAL_COLLECTIONS) val total_collections: Int,
    @SerializedName(JSON_KEY_FOLLOWED_BY_USER) val followed_by_user: Boolean,
    @SerializedName(JSON_KEY_DOWNLOADS) val downloads: Int,
    @SerializedName(JSON_KEY_UPLOADS_REMAINING) val uploads_remaining: Int,
    @SerializedName(JSON_KEY_INSTAGRAM_USERNAME) val instagram_username: String,
    @SerializedName(JSON_KEY_EMAIL) val email: String,
    @SerializedName(JSON_KEY_LINKS) val links: User.UserLinks
) {
    companion object {
        private const val JSON_KEY_ID = "id"
        private const val JSON_KEY_UPDATED_AT = "updated_at"
        private const val JSON_KEY_USERNAME = "username"
        private const val JSON_KEY_FIRST_NAME = "first_name"
        private const val JSON_KEY_LAST_NAME = "last_name"
        private const val JSON_KEY_TWITTER_USERNAME = "twitter_username"
        private const val JSON_KEY_PORTFOLIO_URL = "portfolio_url"
        private const val JSON_KEY_BIO = "bio"
        private const val JSON_KEY_LOCATION = "location"
        private const val JSON_KEY_TOTAL_LIKES = "total_likes"
        private const val JSON_KEY_TOTAL_PHOTOS = "total_photos"
        private const val JSON_KEY_TOTAL_COLLECTIONS = "total_collections"
        private const val JSON_KEY_FOLLOWED_BY_USER = "followed_by_user"
        private const val JSON_KEY_DOWNLOADS = "downloads"
        private const val JSON_KEY_UPLOADS_REMAINING = "uploads_remaining"
        private const val JSON_KEY_INSTAGRAM_USERNAME = "instagram_username"
        private const val JSON_KEY_EMAIL = "email"
        private const val JSON_KEY_LINKS = "links"
    }
}

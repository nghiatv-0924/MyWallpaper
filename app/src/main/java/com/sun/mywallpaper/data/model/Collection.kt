package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName(JSON_KEY_ID) val id: Int,
    @SerializedName(JSON_KEY_TITLE) val title: String,
    @SerializedName(JSON_KEY_DESCRIPTION) val description: String,
    @SerializedName(JSON_KEY_PUBLISHED_AT) val publishedAt: String,
    @SerializedName(JSON_KEY_UPDATED_AT) val updatedAt: String,
    @SerializedName(JSON_KEY_CURATED) val curated: Boolean,
    @SerializedName(JSON_KEY_TOTAL_PHOTOS) val totalPhotos: Int,
    @SerializedName(JSON_KEY_PRIVATE) val isPrivate: Boolean,
    @SerializedName(JSON_KEY_SHARE_KEY) val shareKey: String,
    @SerializedName(JSON_KEY_COVER_PHOTO) val coverPhoto: Photo,
    @SerializedName(JSON_KEY_USER) val user: User,
    @SerializedName(JSON_KEY_LINKS) val links: CollectionLinks
) {
    data class CollectionLinks(
        @SerializedName(JSON_KEY_LINKS_SELF) val self: String,
        @SerializedName(JSON_KEY_LINKS_HTML) val html: String,
        @SerializedName(JSON_KEY_LINKS_PHOTOS) val photos: String,
        @SerializedName(JSON_KEY_LINKS_RELATED) val related: String
    )

    companion object {
        private const val JSON_KEY_ID = "id"
        private const val JSON_KEY_TITLE = "title"
        private const val JSON_KEY_DESCRIPTION = "description"
        private const val JSON_KEY_PUBLISHED_AT = "published_at"
        private const val JSON_KEY_UPDATED_AT = "updated_at"
        private const val JSON_KEY_CURATED = "curated"
        private const val JSON_KEY_TOTAL_PHOTOS = "total_photos"
        private const val JSON_KEY_PRIVATE = "private"
        private const val JSON_KEY_SHARE_KEY = "share_key"
        private const val JSON_KEY_COVER_PHOTO = "cover_photo"
        private const val JSON_KEY_USER = "user"
        private const val JSON_KEY_LINKS = "links"
        private const val JSON_KEY_LINKS_SELF = "self"
        private const val JSON_KEY_LINKS_HTML = "html"
        private const val JSON_KEY_LINKS_PHOTOS = "photos"
        private const val JSON_KEY_LINKS_RELATED = "related"
    }
}

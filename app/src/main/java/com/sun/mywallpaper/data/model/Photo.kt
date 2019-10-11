package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName(JSON_KEY_ID) val id: String,
    @SerializedName(JSON_KEY_CREATED_AT) val createdAt: String,
    @SerializedName(JSON_KEY_UPDATED_AT) val updatedAt: String,
    @SerializedName(JSON_KEY_WIDTH) val width: Int,
    @SerializedName(JSON_KEY_HEIGHT) val height: Int,
    @SerializedName(JSON_KEY_COLOR) val color: String,
    @SerializedName(JSON_KEY_DOWNLOADS) val downloads: Int,
    @SerializedName(JSON_KEY_LIKES) val likes: Int,
    @SerializedName(JSON_KEY_LIKED_BY_USER) val likedByUser: Boolean,
    @SerializedName(JSON_KEY_DESCRIPTION) val description: String,
    @SerializedName(JSON_KEY_EXIF) val exif: Exif,
    @SerializedName(JSON_KEY_LOCATION) val location: Location,
    @SerializedName(JSON_KEY_CURRENT_USER_COLLECTIONS) val currentUserCollections: List<Collection>,
    @SerializedName(JSON_KEY_URLS) val urls: PhotoUrls,
    @SerializedName(JSON_KEY_CATEGORIES) val categories: List<Category>,
    @SerializedName(JSON_KEY_LINKS) val links: PhotoLinks,
    @SerializedName(JSON_KEY_USER) val user: User,
    @SerializedName(JSON_KEY_STORY) val story: Story
) {

    data class Exif(
        @SerializedName(JSON_KEY_EXIF_MAKE) val make: String,
        @SerializedName(JSON_KEY_EXIF_MODEL) val model: String,
        @SerializedName(JSON_KEY_EXIF_EXPOSURE_TIME) val exposureTime: String,
        @SerializedName(JSON_KEY_EXIF_APERTURE) val aperture: String,
        @SerializedName(JSON_KEY_EXIF_FOCAL_LENGTH) val focalLength: String,
        @SerializedName(JSON_KEY_EXIF_ISO) val iso: Int
    )

    data class PhotoUrls(
        @SerializedName(JSON_KEY_URLS_RAW) val raw: String,
        @SerializedName(JSON_KEY_URLS_FULL) val full: String,
        @SerializedName(JSON_KEY_URLS_REGULAR) val regular: String,
        @SerializedName(JSON_KEY_URLS_SMALL) val small: String,
        @SerializedName(JSON_KEY_URLS_THUMB) val thumb: String
    )

    data class PhotoLinks(
        @SerializedName(JSON_KEY_LINKS_SELF) val self: String,
        @SerializedName(JSON_KEY_LINKS_HTML) val html: String,
        @SerializedName(JSON_KEY_LINKS_DOWNLOAD) val download: String,
        @SerializedName(JSON_KEY_LINKS_DOWNLOAD_LOCATION) val downloadLocation: String
    )

    data class Story(
        @SerializedName(JSON_KEY_STORY_TITLE) val title: String,
        @SerializedName(JSON_KEY_STORY_DESCRIPTION) val description: String,
        @SerializedName(JSON_KEY_STORY_IMAGE_URL) val image_url: String
    )

    companion object {
        private const val JSON_KEY_ID = "id"
        private const val JSON_KEY_CREATED_AT = "created_at"
        private const val JSON_KEY_UPDATED_AT = "updated_at"
        private const val JSON_KEY_WIDTH = "width"
        private const val JSON_KEY_HEIGHT = "height"
        private const val JSON_KEY_COLOR = "color"
        private const val JSON_KEY_DOWNLOADS = "downloads"
        private const val JSON_KEY_LIKES = "likes"
        private const val JSON_KEY_LIKED_BY_USER = "liked_by_user"
        private const val JSON_KEY_DESCRIPTION = "description"
        private const val JSON_KEY_EXIF = "exif"
        private const val JSON_KEY_EXIF_MAKE = "make"
        private const val JSON_KEY_EXIF_MODEL = "model"
        private const val JSON_KEY_EXIF_EXPOSURE_TIME = "exposure_time"
        private const val JSON_KEY_EXIF_APERTURE = "aperture"
        private const val JSON_KEY_EXIF_FOCAL_LENGTH = "focal_length"
        private const val JSON_KEY_EXIF_ISO = "iso"
        private const val JSON_KEY_LOCATION = "location"
        private const val JSON_KEY_CURRENT_USER_COLLECTIONS = "current_user_collections"
        private const val JSON_KEY_URLS = "urls"
        private const val JSON_KEY_URLS_RAW = "raw"
        private const val JSON_KEY_URLS_FULL = "full"
        private const val JSON_KEY_URLS_REGULAR = "regular"
        private const val JSON_KEY_URLS_SMALL = "small"
        private const val JSON_KEY_URLS_THUMB = "thumb"
        private const val JSON_KEY_CATEGORIES = "categories"
        private const val JSON_KEY_LINKS = "links"
        private const val JSON_KEY_LINKS_SELF = "self"
        private const val JSON_KEY_LINKS_HTML = "html"
        private const val JSON_KEY_LINKS_DOWNLOAD = "download"
        private const val JSON_KEY_LINKS_DOWNLOAD_LOCATION = "download_location"
        private const val JSON_KEY_USER = "user"
        private const val JSON_KEY_STORY = "story"
        private const val JSON_KEY_STORY_TITLE = "title"
        private const val JSON_KEY_STORY_DESCRIPTION = "description"
        private const val JSON_KEY_STORY_IMAGE_URL = "image_url"
    }
}

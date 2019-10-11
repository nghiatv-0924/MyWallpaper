package com.sun.mywallpaper.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(JSON_KEY_ID) val id: String,
    @SerializedName(JSON_KEY_UPDATED_AT) val updatedAt: String,
    @SerializedName(JSON_KEY_USERNAME) val username: String,
    @SerializedName(JSON_KEY_NAME) val name: String,
    @SerializedName(JSON_KEY_FIRST_NAME) val firstName: String,
    @SerializedName(JSON_KEY_LAST_NAME) val lastName: String,
    @SerializedName(JSON_KEY_INSTAGRAM_USERNAME) val instagramUsername: String,
    @SerializedName(JSON_KEY_TWITTER_USERNAME) val twitterUsername: String,
    @SerializedName(JSON_KEY_PORTFOLIO_URL) val portfolioUrl: String,
    @SerializedName(JSON_KEY_BIO) val bio: String,
    @SerializedName(JSON_KEY_LOCATION) val location: String,
    @SerializedName(JSON_KEY_TOTAL_LIKES) val totalLikes: Int,
    @SerializedName(JSON_KEY_TOTAL_PHOTOS) val totalPhotos: Int,
    @SerializedName(JSON_KEY_TOTAL_COLLECTIONS) val totalCollections: Int,
    @SerializedName(JSON_KEY_FOLLOWED_BY_USER) val followedByUser: Boolean,
    @SerializedName(JSON_KEY_FOLLOWERS_COUNT) val followersCount: Int,
    @SerializedName(JSON_KEY_FOLLOWING_COUNT) val following_count: Int,
    @SerializedName(JSON_KEY_DOWNLOADS) val downloads: Int,
    @SerializedName(JSON_KEY_PROFILE_IMAGE) val profile_image: ProfileImage,
    @SerializedName(JSON_KEY_BADGE) val badge: Badge,
    @SerializedName(JSON_KEY_LINKS) val links: UserLinks
) {
    data class ProfileImage(
        @SerializedName(JSON_KEY_PROFILE_IMAGE_SMALL) val small: String,
        @SerializedName(JSON_KEY_PROFILE_IMAGE_MEDIUM) val medium: String,
        @SerializedName(JSON_KEY_PROFILE_IMAGE_LARGE) val large: String
    )

    data class Badge(
        @SerializedName(JSON_KEY_BADGE_TITLE) val title: String,
        @SerializedName(JSON_KEY_BADGE_PRIMARY) val primary: Boolean,
        @SerializedName(JSON_KEY_BADGE_SLUG) val slug: String,
        @SerializedName(JSON_KEY_BADGE_LINK) val link: String
    )

    data class UserLinks(
        @SerializedName(JSON_KEY_LINKS_SELF) val self: String,
        @SerializedName(JSON_KEY_LINKS_HTML) val html: String,
        @SerializedName(JSON_KEY_LINKS_PHOTOS) val photos: String,
        @SerializedName(JSON_KEY_LINKS_LIKES) val likes: String,
        @SerializedName(JSON_KEY_LINKS_PORTFOLIO) val portfolio: String
    )

    companion object {
        private const val JSON_KEY_ID = "id"
        private const val JSON_KEY_UPDATED_AT = "updated_at"
        private const val JSON_KEY_USERNAME = "username"
        private const val JSON_KEY_NAME = "name"
        private const val JSON_KEY_FIRST_NAME = "first_name"
        private const val JSON_KEY_LAST_NAME = "last_name"
        private const val JSON_KEY_INSTAGRAM_USERNAME = "instagram_username"
        private const val JSON_KEY_TWITTER_USERNAME = "twitter_username"
        private const val JSON_KEY_PORTFOLIO_URL = "portfolio_url"
        private const val JSON_KEY_BIO = "bio"
        private const val JSON_KEY_LOCATION = "location"
        private const val JSON_KEY_TOTAL_LIKES = "total_likes"
        private const val JSON_KEY_TOTAL_PHOTOS = "total_photos"
        private const val JSON_KEY_TOTAL_COLLECTIONS = "total_collections"
        private const val JSON_KEY_FOLLOWED_BY_USER = "followed_by_user"
        private const val JSON_KEY_FOLLOWERS_COUNT = "followers_count"
        private const val JSON_KEY_FOLLOWING_COUNT = "following_count"
        private const val JSON_KEY_DOWNLOADS = "downloads"
        private const val JSON_KEY_PROFILE_IMAGE = "profile_image"
        private const val JSON_KEY_PROFILE_IMAGE_SMALL = "small"
        private const val JSON_KEY_PROFILE_IMAGE_MEDIUM = "medium"
        private const val JSON_KEY_PROFILE_IMAGE_LARGE = "large"
        private const val JSON_KEY_BADGE = "badge"
        private const val JSON_KEY_BADGE_TITLE = "title"
        private const val JSON_KEY_BADGE_PRIMARY = "primary"
        private const val JSON_KEY_BADGE_SLUG = "slug"
        private const val JSON_KEY_BADGE_LINK = "link"
        private const val JSON_KEY_LINKS = "links"
        private const val JSON_KEY_LINKS_SELF = "self"
        private const val JSON_KEY_LINKS_HTML = "html"
        private const val JSON_KEY_LINKS_PHOTOS = "photos"
        private const val JSON_KEY_LINKS_LIKES = "likes"
        private const val JSON_KEY_LINKS_PORTFOLIO = "portfolio"
    }
}

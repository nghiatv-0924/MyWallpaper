package com.sun.mywallpaper.data.api.response

data class LikePhotoResponse(
    val photo: Photo,
    val user: User
) {
    data class Photo(
        val id: String,
        val color: String,
        val likes: Int
    )

    data class User(
        val id: String,
        val username: String,
        val name: String
    )
}

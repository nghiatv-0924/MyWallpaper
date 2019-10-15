package com.sun.mywallpaper.data.api.response

import com.sun.mywallpaper.data.model.Collection
import com.sun.mywallpaper.data.model.Photo

data class ChangeCollectionPhotoResponse(
    val photo: Photo,
    val collection: Collection,
    val user: User
) {
    data class User(
        val id: String,
        val username: String,
        val name: String,
        val bio: String
    )
}

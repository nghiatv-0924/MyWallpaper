package com.sun.mywallpaper.data.api.response

import com.sun.mywallpaper.data.model.Photo

data class SearchPhotosResponse(
    val total: Int,
    val results: List<Photo>
)

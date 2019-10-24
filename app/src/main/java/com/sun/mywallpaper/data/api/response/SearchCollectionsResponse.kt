package com.sun.mywallpaper.data.api.response

import com.sun.mywallpaper.data.model.Collection

data class SearchCollectionsResponse(
    val total: Int,
    val results: List<Collection>
)

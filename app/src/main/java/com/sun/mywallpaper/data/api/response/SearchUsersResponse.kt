package com.sun.mywallpaper.data.api.response

import com.sun.mywallpaper.data.model.User

data class SearchUsersResponse(
    val total: Int,
    val results: List<User>
)

package com.sun.mywallpaper.viewmodel

import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.data.repository.UserRepository

class UserViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    override fun create() {
    }
}

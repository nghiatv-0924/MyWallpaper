package com.sun.mywallpaper.ui.userdetail

import com.sun.mywallpaper.base.BaseViewModel
import com.sun.mywallpaper.data.repository.UserRepository

class UserDetailViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    override fun create() {
    }
}

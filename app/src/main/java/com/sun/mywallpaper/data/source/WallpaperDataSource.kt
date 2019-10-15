package com.sun.mywallpaper.data.source

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Wallpaper

interface WallpaperDataSource {

    interface Local {

        suspend fun getAllWallpapers(): CoroutineResult<List<Wallpaper>>

        suspend fun deleteAllWallpapers(): CoroutineResult<Boolean>

        suspend fun deleteOldWallpapers(): CoroutineResult<Boolean>

        suspend fun addWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper>

        suspend fun updateWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper>

        suspend fun deleteWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper>
    }
}

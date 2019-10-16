package com.sun.mywallpaper.data.repository

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.model.Wallpaper
import com.sun.mywallpaper.data.source.WallpaperDataSource
import com.sun.mywallpaper.data.source.local.WallpaperLocalDataSource

class WallpaperRepository(private val local: WallpaperLocalDataSource) : WallpaperDataSource.Local {

    override suspend fun getAllWallpapers(): CoroutineResult<List<Wallpaper>> =
        local.getAllWallpapers()

    override suspend fun deleteAllWallpapers(): CoroutineResult<Boolean> =
        local.deleteAllWallpapers()

    override suspend fun deleteOldWallpapers(): CoroutineResult<Boolean> =
        local.deleteOldWallpapers()

    override suspend fun addWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> =
        local.addWallpaper(wallpaper)

    override suspend fun updateWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> =
        local.updateWallpaper(wallpaper)

    override suspend fun deleteWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> =
        local.deleteWallpaper(wallpaper)
}

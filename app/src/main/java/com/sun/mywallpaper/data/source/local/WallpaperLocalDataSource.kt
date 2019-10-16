package com.sun.mywallpaper.data.source.local

import com.sun.mywallpaper.coroutine.CoroutineResult
import com.sun.mywallpaper.data.database.AppDatabase
import com.sun.mywallpaper.data.model.Wallpaper
import com.sun.mywallpaper.data.source.WallpaperDataSource
import com.sun.mywallpaper.util.Constants
import java.util.concurrent.TimeUnit

class WallpaperLocalDataSource(private val appDatabase: AppDatabase) : WallpaperDataSource.Local {

    override suspend fun getAllWallpapers(): CoroutineResult<List<Wallpaper>> {
        val resultData = appDatabase.wallpaperDAO().getAllWallpapers()
        return resultData?.let {
            CoroutineResult.Success(it)
        } ?: CoroutineResult.Error(Exception(Constants.MESSAGE_DATA_NOT_FOUND))
    }

    override suspend fun deleteAllWallpapers(): CoroutineResult<Boolean> = try {
        appDatabase.wallpaperDAO().deleteAllWallpapers()
        CoroutineResult.Success(true)
    } catch (e: Exception) {
        CoroutineResult.Error(e)
    }

    override suspend fun deleteOldWallpapers(): CoroutineResult<Boolean> = try {
        appDatabase.wallpaperDAO().deleteOldWallpapers(System.currentTimeMillis(), ONE_WEEK)
        CoroutineResult.Success(true)
    } catch (e: Exception) {
        CoroutineResult.Error(e)
    }

    override suspend fun addWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> = try {
        appDatabase.wallpaperDAO().addWallpaper(wallpaper)
        CoroutineResult.Success(wallpaper)
    } catch (e: Exception) {
        CoroutineResult.Error(e)
    }

    override suspend fun updateWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> = try {
        appDatabase.wallpaperDAO().updateWallpaper(wallpaper)
        CoroutineResult.Success(wallpaper)
    } catch (e: Exception) {
        CoroutineResult.Error(e)
    }

    override suspend fun deleteWallpaper(wallpaper: Wallpaper): CoroutineResult<Wallpaper> = try {
        appDatabase.wallpaperDAO().deleteWallpaper(wallpaper)
        CoroutineResult.Success(wallpaper)
    } catch (e: Exception) {
        CoroutineResult.Error(e)
    }

    companion object {
        private val ONE_WEEK = TimeUnit.DAYS.toMillis(7)
    }
}

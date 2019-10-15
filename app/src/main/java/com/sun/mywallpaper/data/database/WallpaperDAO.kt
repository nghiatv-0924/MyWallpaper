package com.sun.mywallpaper.data.database

import androidx.room.*
import com.sun.mywallpaper.data.model.Wallpaper

@Dao
interface WallpaperDAO {

    @Query("SELECT * FROM wallpaper_table")
    fun getAllWallpapers(): List<Wallpaper>?

    @Query("DELETE FROM wallpaper_table")
    fun deleteAllWallpapers()

    @Query("DELETE FROM wallpaper_table WHERE :now - date > :threshold")
    fun deleteOldWallpapers(now: Long, threshold: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWallpaper(wallpaper: Wallpaper)

    @Update
    fun updateWallpaper(wallpaper: Wallpaper)

    @Delete
    fun deleteWallpaper(wallpaper: Wallpaper)
}

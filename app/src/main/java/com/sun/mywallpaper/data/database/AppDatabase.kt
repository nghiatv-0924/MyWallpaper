package com.sun.mywallpaper.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sun.mywallpaper.data.model.Wallpaper

@Database(entities = [(Wallpaper::class)], version = AppDatabase.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wallpaperDAO(): WallpaperDAO

    companion object {
        private const val DATABASE_NAME = "my_wallpaper_database"
        internal const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().build().also { INSTANCE = it }
        }
    }
}

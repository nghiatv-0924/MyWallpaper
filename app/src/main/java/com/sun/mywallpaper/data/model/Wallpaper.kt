package com.sun.mywallpaper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sun.mywallpaper.util.Constants.EMPTY_STRING
import com.sun.mywallpaper.util.Constants.UNAVAILABLE_VALUE

@Entity(tableName = Wallpaper.DATABASE_TABLE_WALLPAPER)
data class Wallpaper(
    @PrimaryKey
    @ColumnInfo(name = DATABASE_TABLE_WALLPAPER_COLUMN_ID)
    var id: String = EMPTY_STRING,

    @ColumnInfo(name = DATABASE_TABLE_WALLPAPER_COLUMN_USERNAME)
    var userName: String = EMPTY_STRING,

    @ColumnInfo(name = DATABASE_TABLE_WALLPAPER_COLUMN_THUMBNAIL)
    var thumbnail: String = EMPTY_STRING,

    @ColumnInfo(name = DATABASE_TABLE_WALLPAPER_COLUMN_DATE)
    var date: Long = UNAVAILABLE_VALUE.toLong()
) {
    companion object {
        internal const val DATABASE_TABLE_WALLPAPER = "wallpaper_table"
        private const val DATABASE_TABLE_WALLPAPER_COLUMN_ID = "id"
        private const val DATABASE_TABLE_WALLPAPER_COLUMN_USERNAME = "user_name"
        private const val DATABASE_TABLE_WALLPAPER_COLUMN_THUMBNAIL = "thumbnail"
        private const val DATABASE_TABLE_WALLPAPER_COLUMN_DATE = "date"
    }
}

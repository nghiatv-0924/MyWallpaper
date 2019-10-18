package com.sun.mywallpaper.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object Utils {

    fun isStoragePermissionGranted(activity: Activity) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(activity)) {
                true
            } else {
                requestPermissions(activity)
                false
            }
        } else {
            true
        }

    private fun checkSelfPermission(activity: Activity) = ActivityCompat.checkSelfPermission(
        activity,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(activity: Activity) = ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        1
    )
}

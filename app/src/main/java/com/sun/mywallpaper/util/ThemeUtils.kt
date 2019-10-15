package com.sun.mywallpaper.util

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

object ThemeUtils {


    /*public @StringDef({Theme.LIGHT, Theme.DARK, Theme.BLACK})
    @Retention(RetentionPolicy.SOURCE)
    @interface Theme {
        String LIGHT = "Light";
        String DARK = "Dark";
        String BLACK = "Black";
    }

    public static @Theme String getTheme(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("theme", Theme.LIGHT);
    }*/

    @ColorInt
    fun getThemeAttrColor(context: Context, @AttrRes colorAttr: Int): Int {
        val array = context.obtainStyledAttributes(null, intArrayOf(colorAttr))
        try {
            return array.getColor(0, 0)
        } finally {
            array.recycle()
        }
    }

    @DrawableRes
    fun getThemeAttrDrawable(context: Context, @AttrRes drawableAttr: Int): Int {
        val array = context.obtainStyledAttributes(null, intArrayOf(drawableAttr))
        try {
            return array.getResourceId(0, 0)
        } finally {
            array.recycle()
        }
    }
/*
        public static void setRecentAppsHeaderColor(Activity activity) {
            Bitmap icon = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
            ActivityManager.TaskDescription taskDescription
            = new ActivityManager.TaskDescription(
                    activity.getString(R.string.app_name),
            icon, getThemeAttrColor(activity, R.attr.colorPrimary));
            activity.setTaskDescription(taskDescription);
            if (icon != null) icon.recycle();
        }*/
}

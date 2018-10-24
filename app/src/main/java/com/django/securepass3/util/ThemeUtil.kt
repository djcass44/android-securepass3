package com.django.securepass3.util

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.View
import com.django.securepass3.theme.SystemUtils
import com.django.securepass3.theme.ThemeChoice

class ThemeUtil {
    companion object {
        fun updateSystemUI(activity: Activity, view: View) {
            val dark = ThemeChoice.isDark(activity)

            val window = activity.window

            val backgroundColor = ContextCompat.getColor(activity, if(dark) android.R.color.background_dark else android.R.color.background_light)
            var uiVisibility = view.systemUiVisibility

            if(SystemUtils.supportsSdk23()) {
                uiVisibility = if (dark) uiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()) else uiVisibility.or(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                window.statusBarColor = backgroundColor
                if(SystemUtils.supportsSdk26()) {
                    uiVisibility = if(dark) uiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()) else uiVisibility.or(
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
//                if(SysUtils.supportsSdk28())
//                    window.navigationBarDividerColor = backgroundColor2
                    window.navigationBarColor = backgroundColor
                }
            }
            view.systemUiVisibility = uiVisibility
        }
    }
}
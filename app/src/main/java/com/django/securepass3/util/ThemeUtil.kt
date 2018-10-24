package com.django.securepass3.util

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.django.securepass3.R
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
        fun applyTheme(context: Context, parent: View) {
            val parentLayout = parent as ViewGroup
            val views = arrayListOf<View>()
            views.addAll(addChildren(parentLayout))
            views.add(parentLayout)
            for (i in 0 until views.size) {
                val view = views[i]
                when (view) {
                    is TextView -> {
                        view.setTextColor(when (view.tag) {
                            context.getString(R.string.tag_text_primary) -> {
                                ContextCompat.getColor(context, if (ThemeChoice.isDark(context)) android.R.color.primary_text_dark else android.R.color.primary_text_light)
                            }
                            context.getString(R.string.tag_text_secondary) -> {
                                ContextCompat.getColor(context, if (ThemeChoice.isDark(context)) android.R.color.secondary_text_dark else android.R.color.secondary_text_light)
                            }
                            context.getString(R.string.tag_text_tertiary) -> {
                                ContextCompat.getColor(context, if (ThemeChoice.isDark(context)) android.R.color.tertiary_text_dark else android.R.color.tertiary_text_light)
                            }
                            else -> {
                                view.currentTextColor
                            }
                        })
                    }
                    else -> {
                        if(view.tag == context.getString(R.string.tag_view_background))
                            view.setBackgroundColor(ContextCompat.getColor(context, if(ThemeChoice.isDark(context)) android.R.color.background_dark else android.R.color.background_light))
                    }
                }
            }
        }

        private fun addChildren(viewGroup: ViewGroup): ArrayList<View> {
            val views = arrayListOf<View>()
            for (i in 0 until viewGroup.childCount) {
                val child = viewGroup.getChildAt(i)
                views.add(child)
                if(child is ViewGroup) {
                    views.addAll(addChildren(child))
                }
            }
            return views
        }
    }
}
package com.django.securepass3.extendedcore

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.django.securepass3.R
import com.django.securepass3.theme.ThemeChoice
import com.django.securepass3.util.ThemeUtil

@SuppressLint("Registered")
open class ThemedAppCompatActivity: AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        updateTheme()
    }

    open fun updateTheme() {
        val parentLayout = findViewById<View>(R.id.parentLayout) as ViewGroup
        val views = arrayListOf<View>()
        for (i in 0 until parentLayout.childCount) {
            views.add(parentLayout.getChildAt(i))
        }
        views.add(parentLayout)
        for (i in 0 until views.size) {
            val view = views[i]
            when (view) {
                is TextView -> {
                    view.setTextColor(ContextCompat.getColor(this, when (view.tag) {
                        getString(R.string.tag_text_secondary) -> {
                            if (ThemeChoice.isDark(this)) android.R.color.secondary_text_dark else android.R.color.secondary_text_light
                        }
                        getString(R.string.tag_text_tertiary) -> {
                            if (ThemeChoice.isDark(this)) android.R.color.tertiary_text_dark else android.R.color.tertiary_text_light
                        }
                        else -> {
                            if (ThemeChoice.isDark(this)) android.R.color.primary_text_dark else android.R.color.primary_text_light
                        }
                    }))
                }
                else -> {
                    if(view.tag == getString(R.string.tag_view_background))
                        view.setBackgroundColor(ContextCompat.getColor(this, if(ThemeChoice.isDark(this)) android.R.color.background_dark else android.R.color.background_light))
                }
            }
        }
        ThemeUtil.updateSystemUI(this, parentLayout)
    }
}
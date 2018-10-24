package com.django.securepass3.extendedcore

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.django.securepass3.R
import com.django.securepass3.util.ThemeUtil

@SuppressLint("Registered")
open class ThemedAppCompatActivity: AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        updateTheme()
    }

    open fun updateTheme() {
        val parentLayout = findViewById<View>(R.id.parentLayout)
        ThemeUtil.applyTheme(this, parentLayout)
        ThemeUtil.updateSystemUI(this, parentLayout)
    }
}
package com.django.securepass3.theme

import android.content.Context
import android.content.res.Configuration
import java.util.*

class ThemeChoice {
    enum class Theme {
        LIGHT, DARK, AUTO, SYSTEM
    }
    companion object {
        var theme = Theme.LIGHT

        fun isDark(context: Context): Boolean {
            return when(theme) {
                Theme.DARK -> true
                Theme.AUTO -> appWantsDark()
                Theme.SYSTEM -> systemWantsDark(context)
                else -> false
            }
        }

        private fun systemWantsDark(context: Context): Boolean {
            val nightModeFlags = context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
            return when(nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> true
                else -> false
            }
        }
        private fun appWantsDark(): Boolean {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            return currentHour >= 18 || currentHour < 7
        }
    }
}
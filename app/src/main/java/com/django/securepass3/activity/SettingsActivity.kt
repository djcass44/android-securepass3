package com.django.securepass3.activity

import android.os.Bundle
import android.widget.ImageButton
import com.django.securepass3.R
import com.django.securepass3.extendedcore.ThemedAppCompatActivity

class SettingsActivity : ThemedAppCompatActivity() {
    private lateinit var buttonBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        buttonBack = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}

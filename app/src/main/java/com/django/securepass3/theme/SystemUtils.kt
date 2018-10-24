package com.django.securepass3.theme

import android.os.Build

class SystemUtils {
    companion object {
        fun supportsSdk28(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }
        fun supportsSdk26(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        }
        fun supportsSdk25(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
        }
        fun supportsSdk23(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }
    }
}
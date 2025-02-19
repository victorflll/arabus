package com.example.arabus.ui.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("arabus_prefs", Context.MODE_PRIVATE)

    fun setTalkBackEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("talkback_enabled", enabled).apply()
    }

    fun isTalkBackEnabled(): Boolean {
        return sharedPreferences.getBoolean("talkback_enabled", false)
    }
}

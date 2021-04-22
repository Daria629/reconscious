package com.medical.reconscious.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private var KEY = "key"
    private var TOKEN = "token"

    private val preferences: SharedPreferences = context.getSharedPreferences("preference", Context.MODE_PRIVATE)

    var key: String?
        get() = preferences.getString(KEY, "")
        set(value) = preferences.edit().putString(KEY, value).apply()

    var token: String?
        get() = preferences.getString(TOKEN, "")
        set(value) = preferences.edit().putString(TOKEN, value).apply()
}
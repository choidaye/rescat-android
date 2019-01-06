package com.rescat.rescat_android.Preference

import android.content.Context
import android.content.SharedPreferences

class RescatPreference(ctx: Context) {

    private val PREFS_FILENAME: String = "prefs"
    private val USER_TOKEN: String = "user_token"
    private val LONGITUDE: String = "longitude"
    private val LATITUDE: String = "latitude"

    private val prefs: SharedPreferences = ctx.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var token: String
        get() = prefs.getString(USER_TOKEN, "")
        set(value) = prefs.edit().putString(USER_TOKEN, value).apply()

    var longitude: Float
        get() = prefs.getFloat(LONGITUDE, 0F)
        set(value) = prefs.edit().putFloat(LONGITUDE, value).apply()

    var latitude: Float
        get() = prefs.getFloat(LATITUDE, 0F)
        set(value) = prefs.edit().putFloat(LATITUDE, value).apply()

    fun clear() {
        prefs.edit().clear().commit()
    }
}
package com.rescat.rescat_android.Preference

import android.content.Context
import android.content.SharedPreferences

class RescatPreference(ctx: Context) {

    private val PREFS_FILENAME: String = "prefs"
    private val USER_TOKEN: String = "user_token"

    private val LONGITUDE: String = "lng"
    private val LATITUDE: String = "lat"
    private val DEVICE_TOKEN: String = "deivceToken"

    private val NICKNAME: String = ""
    private val IS_CARETAKER: String = "0" // 0: 아님 1: 케어테이커임

    private val prefs: SharedPreferences = ctx.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)


    var token: String
        get() = prefs.getString(USER_TOKEN, "")
        set(value) = prefs.edit().putString(USER_TOKEN, value).apply()

    var lng: Float
        get() = prefs.getFloat(LONGITUDE, 0F)
        set(value) = prefs.edit().putFloat(LONGITUDE, value).apply()

    var lat: Float
        get() = prefs.getFloat(LATITUDE, 0F)
        set(value) = prefs.edit().putFloat(LATITUDE, value).apply()

    var deviceToken: String
        get() = prefs.getString(DEVICE_TOKEN, "")
        set(value) = prefs.edit().putString(DEVICE_TOKEN, value).apply()

    var nickname: String
        get() = prefs.getString(NICKNAME, "")
        set(value) = prefs.edit().putString(NICKNAME, value).apply()

    var careTaker: String
        get() = prefs.getString(IS_CARETAKER, "0")
        set(value) = prefs.edit().putString(IS_CARETAKER, value).apply()

    fun clear() {
        prefs.edit().clear().commit()
    }
}
package com.rescat.rescat_android.Post

/**
 * Created by gominju on 08/01/2019.
 */

data class CareTakerAuth(
        val authenticationPhotoUrl: String,
        var nickname : String,
        var phone: String,
        var regionFullName : String,
        var type: Int,
        var name: String
)
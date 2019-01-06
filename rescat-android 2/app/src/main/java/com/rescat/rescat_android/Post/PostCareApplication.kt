package com.rescat.rescat_android.Post

data class PostCareApplication(
    val type: Int,
    val name: String,
    val phone: String,
    val birth: String,
    val address : String,
    val job: String,
    val houseType: String,
    val companionExperience :Boolean,
    val finalWord : String
)
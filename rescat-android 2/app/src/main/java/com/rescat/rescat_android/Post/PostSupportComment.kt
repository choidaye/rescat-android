package com.rescat.rescat_android.Post

data class PostSupportComment(
    val contents : String,
    val createdAt : String,
    val idx : Int,
    val isWriter : Boolean,
    val nickname : String,
    val photoUrl : String

)
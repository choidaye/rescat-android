package com.rescat.rescat_android.model

data class CommentData(
    val createdAt : String,
    val idx : Int,
    val contents : String,
    val photoUrl : String,
    val nickname : String,
    val userRole : String

)
package com.rescat.rescat_android.model

data class SearchData(
    val s_id : Int,
    val s_title : String,
    val s_contents : String,
    val s_category : String,
    val u_id : Int,
    val s_photo : String,
    val auth : Boolean,
    var like : Boolean
)
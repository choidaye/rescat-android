package com.rescat.rescat_android.Post

data class PostFundAdd(
    val account : String,
    val bankName : String,
    val category : Int,
    val certificationUrls : ArrayList<String>,
    val contents : String,
    val goalAmount : Int,
    val introduction : String,
    val limitAt : String,
    val mainRegion : String,
    val name : String,
    val phone : String,
    val photoUrls : ArrayList<String>,
    val title : String
)
package com.rescat.rescat_android.model

/*
     type
        0 : 입양
        1 : 임시보호
 */
data class HelpCardData(
    val idx : Int,
    val name : String,
    val contents : String,
    val photo : PhotoData,
    val type : Int,
    val viewCount : Int,
    val createdAt : String,
    val updatedAt : String,
    val isFinished : Boolean
)
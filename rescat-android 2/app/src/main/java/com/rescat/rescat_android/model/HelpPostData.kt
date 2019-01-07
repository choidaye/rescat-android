package com.rescat.rescat_android.model

data class HelpPostData (
    val idx : Int,
    val name : String,
    val contents : String,
    val photos : ArrayList<PhotoData>,
    val type : Int,
    val viewCount : Int,
    val createdAt : String,
    val isFinished : Boolean,
    val age : String,
    val sex : Int,
    val breed : String,
    val vaccination : String,
    val tnr : Int,
    val etc : String,
    val startProtectionPeriod : String,
    val endProtectionPeriod : String,
    val isConfirmed : Int,
    val updatedAt : String,
    val nickname: String,
    val isSubmitted : Boolean,
    val isWriter : Boolean
)

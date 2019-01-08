package com.rescat.rescat_android.Get



data class GetMyNoticeResponse(
    val createdAt : String,
    val idx : Long,
    val notification : Notification,
    val isChecked : Int
)

data class Notification(
    val createdAt : String,
    val idx : Long,
    val contents : String,
    val targetType : String,
    val targetIdx : Long,
    val targetIdxNull : Boolean
)
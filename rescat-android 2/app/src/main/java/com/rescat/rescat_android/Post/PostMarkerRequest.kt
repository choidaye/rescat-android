package com.rescat.rescat_android.Post

data class PostMarkerRequest(
    val requestType : Int,
    val registerType : Int,
    val name : String,
    val etc : String,
    val lat : Double,
    val lng : Double,
    val address : String,
    val phone : String,
    val radius : Int,
    val sex : Int,
    val age : String,
    val tnr : Int,
    val markerIdx : Int,
    val photoUrl : String,
    val regionFullName : String
)
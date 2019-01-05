package com.rescat.rescat_android.model

data class MapData(
    val location_id : Int,
    val longitude : Double,
    val latitude : Double,
    val location_type : Int,
    val createdAt : String,
    val updatedAt : String
)
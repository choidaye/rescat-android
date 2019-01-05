package com.rescat.rescat_android.model

import android.graphics.Region

data class MapData(
    val idx : Int,
    val name : String,
    val lat : Double,
    val lng : Double,
    val radius : Int,
    val sex : Int,
    val age : String,
    val tnr : Int,
    val etc : String,
    val photoUrl : String,
    val region : Region,
    val category : Int,
    val address : String,
    val phone : String

)

data class Region(
    val code : Int,
    val name : String
) 

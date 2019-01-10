package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.MainPhoto

data class GetMainPageFunding(
    val category : Int,
    val currentAmount : Int,
    val goalAmount : Int,
    val idx : Int,
    val introduction : String,
    val limitAt : String,
    val mainPhoto : MainPhoto,
    val name : String,
    val phone : String,
    val title : String
)
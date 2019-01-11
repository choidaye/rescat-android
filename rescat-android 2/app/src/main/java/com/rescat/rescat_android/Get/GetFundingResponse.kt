package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.MainPhoto


//0 : 치료비 모금  1 :프로젝트 모금
data class GetFundingResponse(
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


package com.rescat.rescat_android.Post.Response

import com.rescat.rescat_android.model.RegionsData

data class PostUserLoginResponse(
    val emdCodes : ArrayList<Int>,
    val idx : Int,
    val jwtTokenDto : JwtTokenDto,
    val mileage : Int,
    val regions : ArrayList<String>,
    val role : String,
    val message : String
)


data class JwtTokenDto(
    val token : String
)


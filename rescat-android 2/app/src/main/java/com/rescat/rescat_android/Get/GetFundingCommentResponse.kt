package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.MainRegion
import com.rescat.rescat_android.model.RegionData
import com.rescat.rescat_android.model.SubRegion1
import com.rescat.rescat_android.model.SubRegion2

data class GetFundingCommentResponse(
    val contents : String,
    val createdAt : String,
    val idx : String,
    val isWriter : Boolean,
    val nickname : String,
    val photoUrl : String,
    val status : Status,
    val userRole : String,
    val warning : Int
)

data class Status(
    val createdAt : String,
    val deviceToken : String,
    val id : String,
    val idx : Int,
    val mainRegion : MainRegion,
    val mileage : Int,
    val myRegionDtoList : ArrayList<RegionData>,
    val name : String,
    val phone : String,
    val nickname : String,
    val regionFullName : String,
    val role : String,
    val subRegion1 : SubRegion1,
    val subRegion2 : SubRegion2

)


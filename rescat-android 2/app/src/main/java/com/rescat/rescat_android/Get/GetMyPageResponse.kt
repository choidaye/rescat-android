package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.RegionData

data class GetMyPageResponse(
    var id : String,
    var mileage : Int,
    var nickname : String,
    var phone : String,
    var regions : ArrayList<RegionData>,
    var role : String
)
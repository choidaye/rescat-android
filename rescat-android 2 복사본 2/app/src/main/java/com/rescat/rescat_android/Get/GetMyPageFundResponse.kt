package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.CertificationsData
import com.rescat.rescat_android.model.FundPhotoData


data class GetMyPageFundResponse(
    var createdAt : String,
    var idx : Int,
    var title : String,
    var contents : String,
    var introduction : String,
    var goalAmount : Int,
    var currentAmount : Int,
    var bankName : String,
    var account : String,
    var mainRegion : String,
    var certifications : ArrayList<CertificationsData>,
    var category : Int,
    var photos : ArrayList<FundPhotoData>,
    var limitAt : String,
    var isConfirmed : Int,
    var nickname : String

)
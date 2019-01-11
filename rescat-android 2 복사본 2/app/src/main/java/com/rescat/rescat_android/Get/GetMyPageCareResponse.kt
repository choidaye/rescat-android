package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.PhotoData

data class GetMyPageCareResponse(
    var createdAt : String,
    var idx : Int,
    var contents : String,
    var photos : ArrayList<PhotoData>,
    var name : String,
    var type : Int,
    var age : String,
    var sex : Int,
    var breed : String,
    var vaccination : String,
    var tnr : Int,
    var etc : String,
    var viewCount : Int,
    var startProtectionPeriod : String,
    var endProtectionPeriod : String,
    var isConfirmed : Int,
    var isFinished : Boolean,
    var updatedAt : String,
    var nickname : String,
    var isSubmitted : Boolean,
    var isWriter : Boolean
)
package com.rescat.rescat_android.Get

import com.rescat.rescat_android.model.CertificationsData
import com.rescat.rescat_android.model.PhotoData


data class GetFundingData(
    val account : String,
    val bankName : String,
    val category : Int,
    val certifications : ArrayList<CertificationsData>,
    val contents : String,
    val createdAt : String,
    val currentAmount : Int,
    val goalAmount : Int,
    val idx : Int,
    val introduction : String,
    val isConfirmed : Int,
    val isWriter : Boolean,
    val limitAt : String,
    val mainRegion : String,
    val name : String,
    val nickname : String,
    val phone : String,
    val photos : ArrayList<PhotoData>,
    val title : String,
    val warning : Int
)
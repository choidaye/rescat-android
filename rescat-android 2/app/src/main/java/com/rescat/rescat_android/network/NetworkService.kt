package com.rescat.rescat_android.network

import com.google.gson.JsonObject
import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.Get.GetMyPageResponse
import com.rescat.rescat_android.Post.PostUserLogin
import com.rescat.rescat_android.Post.PostUserSignUp
import com.rescat.rescat_android.Post.Response.PostUserLoginResponse
import com.rescat.rescat_android.Post.Response.PostUserSignUpResponse
import retrofit2.Call
import retrofit2.http.*


interface NetworkService{

    //일반유저 생성
    @POST("api/users")
    fun postUserSignUp(
        @Body post : PostUserSignUp
    ): Call<PostUserSignUpResponse>

    //유저 로그인
    @POST("api/users/login")
    fun postUserLogin(
        @Body post : PostUserLogin
    ): Call<PostUserLoginResponse>

    //지도 마커 조회
    @GET("api/maps")
    fun getMapResponse(
        @Header("Authorization") auth : String,
        @Query("emdCode") emdcode : String
    ): Call<GetMapResponse>

    //지도 마커 요청 및 수정


    //마이페이지 조회
    @GET("api/users/mypage")
    fun getMyPage(
        @Header("Authorization") auth : String
    ): Call<GetMyPageResponse>




}
package com.rescat.rescat_android.network

import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.Get.GetMyPageCareResponse
import com.rescat.rescat_android.Get.GetMyPageResponse
import com.rescat.rescat_android.Post.PostUserLogin
import com.rescat.rescat_android.Post.PostUserSignUp
import com.rescat.rescat_android.Post.Response.PostMarkerRequestResponse
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
        @Header("Authorization") token : String,
        @Query("emdCode") emdcode : String
    ): Call<ArrayList<GetMapResponse>>

    //지도 마커 요청 및 수정
    @POST("api/maps")
    fun postMapResponse(
        @Header("Authorization") token : String,
        @Body  mapRequest : String
    ) : Call<ArrayList<PostMarkerRequestResponse>>


    //마이페이지 조회
    @GET("api/users/mypage")
    fun getMyPage(
        @Header("Authorization") token : String
    ): Call<GetMyPageResponse>


    //마이페이지 입양/임시보호 내가 쓴 글 조회
    @GET("api/users/mypage/care-posts")
    fun getMyPostCare(
        @Header("Authorization") token : String
    ): Call<ArrayList<GetMyPageCareResponse>>



}
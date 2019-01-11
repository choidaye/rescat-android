package com.rescat.rescat_android.network

import com.google.gson.JsonObject
import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.Post.*
import com.rescat.rescat_android.Post.Response.*
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.model.HelpCardData
import com.rescat.rescat_android.model.HelpPostData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface NetworkService {

    //일반유저 생성
    @POST("api/users")
    fun postUserSignUp(
            @Body post: PostUserSignUp
    ): Call<PostUserSignUpResponse>

    //유저 로그인
    @POST("api/users/login")
    fun postUserLogin(
            @Body post: PostUserLogin
    ): Call<PostUserLoginResponse>

    //지도 마커 조회
    @GET("api/maps")
    fun getMapResponse(
            @Header("Authorization") auth: String,
            @Query("emdCode") emdcode: String
    ): Call<GetMapResponse>

    // 케어테이커 번호 인증
    @FormUrlEncoded
    @POST("/api/users/authentications/phone")
    fun getCareTakerAuthCode(
            @Field("phone") phone: String
    ): Call<CareTakerMobileAuthResponse>

    // 케어테이커 인증 요청
    @POST("/api/users/authentications/caretaker")
    fun getCareTakerAuth(
            @Header("Authorization") auth: String,
            @Body post: CareTakerAuth
    ): Call<Void>

    // 사진 컨트롤러 (서버에서 photoUri 보내줌)
    @Multipart
    @POST("/api/photo")
    fun getPhtoUri(
            @Header("Authorization") auth: String,
            @Part data: MultipartBody.Part
    ): Call<PhotoControllerResponse>


    // ㅇㅕ기서부터 추가함
    //입양&임시보호 데이터 리스트 조회
    @GET("api/care-posts")
    fun getHelpCareCard(
            @Query("type") type: Int
    ): Call<ArrayList<HelpCardData>>

    //입양&임시보호 메인 데이터
    @GET("api/care-posts/main")
    fun getHelpPostMain(
    ): Call<ArrayList<HelpCardData>>

    //입양&임시보호 데이터 조회
    @GET("api/care-posts/{idx}")
    fun getHelpDetailData(
            @Path("idx") idx: Int
    ): Call<HelpPostData>

    //입양& 임시 보호 댓글 조회
    @GET("api/care-posts/{idx}/comments")
    fun getCarePostComment(
            @Path("idx") idx: Int
    ): Call<ArrayList<CommentData>>

    //입양 & 임시보호 댓글 등록
    @POST("api/care-posts/{idx}/comments")
    fun postCarePostComment(
            @Header("Authorization") auth: String,
            @Body post: PostCareComment,
            @Path("idx") idx: Int
    ): Call<CommentData>

    //입양 & 임시보호 신청
    @POST("/api/care-posts/{idx}/applications")
    fun postCareApplication(
            @Header("Authorization") auth: String,
            @Body post: PostCareApplication,
            @Path("idx") idx: Int
    ): Call<ResponseBody>


}
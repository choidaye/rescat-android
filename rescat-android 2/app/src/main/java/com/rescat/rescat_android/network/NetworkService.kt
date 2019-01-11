package com.rescat.rescat_android.network

import com.rescat.rescat_android.Get.*
import com.rescat.rescat_android.Post.PostCareApplication
import com.rescat.rescat_android.Post.PostCareComment
import com.rescat.rescat_android.Post.PostUserLogin
import com.rescat.rescat_android.Post.PostUserSignUp
import com.rescat.rescat_android.Post.Response.PostMarkerRequestResponse
import com.rescat.rescat_android.Post.Response.PostUserLoginResponse
import com.rescat.rescat_android.Post.Response.PostUserSignUpResponse
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.model.HelpCardData
import com.rescat.rescat_android.model.HelpPostData
import com.rescat.rescat_android.model.RegionData
import okhttp3.ResponseBody
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
        @Query("emdCode") emdcode : Int?
    ): Call<ArrayList<GetMapResponse>>

    //지도 마커 요청 및 수정
    @POST("api/maps")
    fun postMapResponse(
        @Body  mapRequest : String
    ) : Call<ArrayList<PostMarkerRequestResponse>>



    //마이페이지 조회
    @GET("api/users/mypage")
    fun getMyPage(): Call<GetMyPageResponse>


    //마이페이지 입양/임시보호 내가 쓴 글 조회
    @GET("api/users/mypage/care-posts")
    fun getMyPostCare(): Call<ArrayList<GetMyPageCareResponse>>



    //마이페이지 닉네임 변경
    @PUT("api/users/mypage/edit")
    fun putMyNicknameModify(
        @Query("nickname") nickname : String
    ) : Call<Unit>


    //마이페이지 닉네임 중복확인
    @POST("api/users/duplicate/nickname")
    fun putNicknameCheck(
        @Query("nicknamee") nickname :String
    ) : Call<Unit>

    //마이페이지 후원 내가 쓴 글 조회
    @GET("api/users/mypage/fundings")
    fun getMyPostFund(): Call<ArrayList<GetMyPageFundResponse>>

    //마이페이지 내가 후원한 글 조회
    @GET("api/users/mypage/supporting")
    fun getMySupprting():Call<ArrayList<GetMySupportingResponse>>


    //마이페이지 비밀번호 변경
    @PUT("api/users/mypage/edit/password")
    fun putPasswordModify(
        @Body userPasswordDto : String
    ) : Call<Unit>


    //입양&임시보호 데이터 리스트 조회
    @GET("api/care-posts")
    fun getHelpCareCard(
        @Query("type") type : Int
    ): Call<ArrayList<HelpCardData>>

    //입양&임시보호 메인 데이터
    @GET("api/care-posts/main")
    fun getHelpPostMain(
    ): Call<ArrayList<HelpCardData>>

    //입양&임시보호 데이터 조회
    @GET("api/care-posts/{idx}")
    fun getHelpDetailData(
        @Path("idx") idx : Int
    ): Call<HelpPostData>

    //입양& 임시 보호 댓글 조회
    @GET("api/care-posts/{idx}/comments")
    fun getCarePostComment(
        @Path("idx") idx : Int
    ): Call<ArrayList<CommentData>>

    //입양 & 임시보호 댓글 등록
    @POST("api/care-posts/{idx}/comments")
    fun postCarePostComment(
        @Body post : PostCareComment,
        @Path("idx") idx : Int
    ): Call<CommentData>

    //입양 & 임시보호 신청
    @POST("/api/care-posts/{idx}/applications")
    fun postCareApplication (
        @Body post : PostCareApplication,
        @Path("idx") idx : Int
    ): Call<ResponseBody>


    //입양 임시보호 댓글 지우기
    @DELETE("/api/care-posts/{idx}/comments/{comment-idx}")
    fun deleteCarePostComment(
        @Header("Authorization") auth : String,
        @Path("idx") idx : Int,
        @Path("comment-idx") comment_idx : Int
    ): Call<ResponseBody>

    //입양 임시보호 신고하기
    @GET("/api/care-posts/{idx}/comments/{comment-idx}/warning")
    fun reportCarePostComment(
        @Header("Authorization") auth : String,
        @Path("idx") idx : Int,
        @Path("comment-idx") comment_idx : Int
    ): Call<ResponseBody>



    //치료비&프로젝트 후원 데이터 리스트 조회
   @GET("api/fundings")
   fun getFundingMainList(
        @Query ("category") category : Int
    ) : Call<ArrayList<GetFundingResponse>>

    //후원할래요 댓글 조회
    @GET("api/fundings/{idx}/comments")
    fun getFundingComment(
        @Path("idx") idx : Int,
        @Query("present") present : Boolean
    ): Call<ArrayList<GetFundingCommentResponse>>

    //후원할래요 데이터 조회
    @GET("api/fundings/{idx}")
    fun getFundingData(
        @Path("idx") idx : Int,
        @Query("present") present : Boolean
    ):Call<GetFundingData>


    //알람 리스트 조회
    @GET("api/users/mypage/notification-box")
    fun getMyNoticeList(
    ): Call<ArrayList<GetMyNoticeResponse>>


    //케어테이커 지역 조회
    @GET("api/users/mypage/regions")
    fun getMyLocation(
    ):Call<ArrayList<RegionData>>

    //펀딩 글 4개 리스트
    @GET("api/fundings/main")
    fun getMainFunding():Call<ArrayList<GetMainPageFunding>>







}
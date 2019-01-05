package com.rescat.rescat_android.ui.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rescat.rescat_android.Get.GetMyPageResponse
import com.rescat.rescat_android.Post.PostUserLogin
import com.rescat.rescat_android.Post.Response.PostUserLoginResponse
import com.rescat.rescat_android.Preference.RescatPreference
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.mypage.MyLocationActivity
import com.rescat.rescat_android.ui.activity.mypage.MyPostActivity
import com.rescat.rescat_android.ui.activity.mypage.NoticeActivity
import com.rescat.rescat_android.ui.activity.mypage.QuestionActivity
import kotlinx.android.synthetic.main.fragment_my_page_member.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageMemberFragment: Fragment(){


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnCLickListener()
        getMyPageResponse()


    }

    private fun setOnCLickListener() {
        btn_fg_my_page_go_notice.setOnClickListener {
            startActivity<NoticeActivity>()
        }

        btn_ll_my_page_setting_location.setOnClickListener {
            startActivity<MyLocationActivity>()
        }

        btn_my_page_my_post.setOnClickListener {
            startActivity<MyPostActivity>()
        }

        btn_my_page_question.setOnClickListener {
            startActivity<QuestionActivity>()
        }
    }

    private fun getMyPageResponse() {

        val token : String = RescatApplication.preference.token

        val getMyPageResponse : Call<GetMyPageResponse> =
                networkService.getMyPage(token)
            getMyPageResponse.enqueue(object :Callback<GetMyPageResponse>{
                override fun onFailure(call: Call<GetMyPageResponse>, t: Throwable) {
                    Log.e("My page fail",t.toString())
                }

                override fun onResponse(call: Call<GetMyPageResponse>, response: Response<GetMyPageResponse>) {
                    if (response.isSuccessful){
                        val id : String = response.body()!!.nickname
                        val nickname : String = response.body()!!.id

                        setMyPageView(id,nickname)
                    }
                }


            })

    }


    private fun setMyPageView(id : String, nickname : String){
        id?.let {
            tv_fg_my_page_id.text = id
        }
        nickname?.let {
            tv_fg_my_page_nickname.text = nickname
        }

    }



}
package com.rescat.rescat_android.ui.activity.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rescat.rescat_android.Get.GetMyPageResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import kotlinx.android.synthetic.main.activity_modify_my_info.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_my_page_member.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyMyInfoActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_my_info)


        getMyPageResponse()
        setOnCLickListener()
    }


    private fun setOnCLickListener() {
        btn_ac_modify_my_info_nickname.setOnClickListener {
            startActivity<ModifyNicknameActivity>()
        }
    }


    private fun getMyPageResponse() {

        Log.e("response","리스폰스 들어옴")

        val getMyPageResponse : Call<GetMyPageResponse> =
            networkService.getMyPage()
        getMyPageResponse.enqueue(object : Callback<GetMyPageResponse> {
            override fun onFailure(call: Call<GetMyPageResponse>, t: Throwable) {
                Log.e("My page fail",t.toString())
            }

            override fun onResponse(call: Call<GetMyPageResponse>, response: Response<GetMyPageResponse>) {
                if (response.isSuccessful){
                    val nickname : String = response.body()!!.nickname
                    val id : String = response.body()!!.id
                    val name : String = response.body()!!.name
                    val phone : String = response.body()!!.phone
                    val role : String = response.body()!!.role

                    if (role == "CARETAKER"){
                        rl_ac_modify_my_info_phonenum.setVisibility(View.VISIBLE)
                    }

                    setMyPageView(name,nickname,id,phone)

                }
            }
        })
    }

    private fun setMyPageView(id : String, name : String, nickname : String, phone : String){
        name?.let {
            tv_ac_my_info_name.text = name
        }
        nickname?.let {
            tv_my_info_nickname.text = nickname
        }

        id?.let{
            tv_my_info_id.text = id
        }

        phone?.let{
            tv_my_info_phone.text = phone
        }

    }




}

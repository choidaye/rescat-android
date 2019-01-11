package com.rescat.rescat_android.ui.activity.mypage

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log

import com.rescat.rescat_android.R
import com.rescat.rescat_android.R.id.et_ac_modify_my_nickname
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import kotlinx.android.synthetic.main.activity_modify_nickname.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyNicknameActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_nickname)

        setOnCLickListener()
    }

    private fun setOnCLickListener() {
        btn_ac_modify_my_nickname_ok.setOnClickListener {
            PutMyNicknameModify()
        }

        btn_password_check.setOnClickListener {
            PutNicknameCheck()
        }
    }

    private fun PutNicknameCheck() {
        Log.e("nickname check", " 리스폰 들어옴")

        val input_nickname: String = et_ac_modify_my_nickname.text.toString()

        val putNicknameCheck : Call<Unit> =
                networkService.putNicknameCheck(input_nickname)
        putNicknameCheck.enqueue(object : Callback<Unit>{

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("nickname check fail",t.toString())
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful){
                   toast("닉네임을 사용할 수 있습니다.")

                } else{
                    toast("닉네임을 다시 적어주세요")
                }
            }

        })
    }



    private fun PutMyNicknameModify() {

        Log.e("nickname response", " 리스폰 들어옴")

        val input_nickname: String = et_ac_modify_my_nickname.text.toString()

        val putModifyNickname:Call<Unit> =
            networkService.putMyNicknameModify(input_nickname)
        putModifyNickname.enqueue(object : Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("Modify nickname Fail", t.toString())
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    startActivity<ModifyMyInfoActivity>()
                }

               else {
                    toast("닉네임 중복확인을 해주세요")
                }
            }
        })

    }
}

package com.rescat.rescat_android.ui.activity.mypage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.rescat.rescat_android.Post.PostUserSignUp
import com.rescat.rescat_android.Post.Response.PostUserSignUpResponse
import com.rescat.rescat_android.Put.PutModifyPassword
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_modify_nickname.*
import kotlinx.android.synthetic.main.activity_modify_pw.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class ModifyPasswordActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_pw)

        setOnCLickListener()
    }

    private fun setOnCLickListener() {
        btn_ac_modify_my_password_ok.setOnClickListener {

            PutModifyPW()

        }

    }



    private fun PutModifyPW() {

        var input_my_pw: String = tv_ac_cuurent_my_pw_input.text.toString()
        var input_changepw: String = tv_ac_change_my_pw_input.text.toString()
        var input_changepw_check: String = tv_ac_change_my_pw_check.text.toString()

        val putModifyPassword:Call<Unit> =
                networkService.putPasswordModify(PutModifyPassword(input_my_pw,input_changepw,input_changepw_check))
        putModifyPassword.enqueue(object : Callback<Unit>{

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("Modify password Fail", t.toString())
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){

                    Log.v("password", " 통신 성공")

                    finish()


                } else {
                    toast("비밀번호를 재 입력해주세요")
                }

            }
        })

    }
}



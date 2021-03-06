package com.rescat.rescat_android.ui.activity.sign

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import android.util.Log
import android.widget.Toast
import com.rescat.rescat_android.Post.PostUserLogin
import com.rescat.rescat_android.Post.Response.PostUserLoginResponse
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        btn_login_go.setOnClickListener {
            PostLoginResponse()
        }

        btn_login_back.setOnClickListener {
            finish()
        }
    }


    private fun PostLoginResponse() {


        if (et_ac_sign_in_id.text.toString().isNotEmpty() && et_ac_sign_in_pw.text.isNotEmpty()) {

            val input_login_id: String = et_ac_sign_in_id.text.toString()
            val input_login_pw: String = et_ac_sign_in_pw.text.toString()
            val deviceToken = ""


            val postLoginResponse: Call<PostUserLoginResponse> =
                networkService.postUserLogin(PostUserLogin(input_login_id, input_login_pw, deviceToken))

            postLoginResponse.enqueue(object : Callback<PostUserLoginResponse> {
                override fun onFailure(call: Call<PostUserLoginResponse>, t: Throwable) {
                    Log.e("Sign In Fail", t.toString())

                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<PostUserLoginResponse>, response: Response<PostUserLoginResponse>) {
                    if (response.isSuccessful) {

                        var token: String = response.body()!!.jwtTokenDto.token
                        Log.v("Sign In Success", "토큰 + " + token)
                        RescatApplication.preference.token = token

                        startActivity<MainActivity>()


                    } else {
                        Toast.makeText(applicationContext,"정보가 일치하지 않습니다",Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }

        else {
            toast("빈칸을 다 채워주세요")
        }
    }

}

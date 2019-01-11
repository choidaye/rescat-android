package com.rescat.rescat_android.ui.activity.sign

import android.os.Build.VERSION_CODES.P
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import com.rescat.rescat_android.Post.PostUserSignUp
import com.rescat.rescat_android.Post.Response.PostUserSignUpResponse

import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_up_ok.setOnClickListener {
            PostUserSignUpResponse()
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_id_valid.setOnClickListener {
            PostIdCCheck()
        }


    }

    private fun PostIdCCheck() {

        if (et_ac_sign_up_id.text.toString().isNotEmpty()){

            val input_id : String = et_ac_sign_up_id.text.toString()


            val postIdCheck : Call<Unit> =
                    networkService.postIdCheck(input_id)
            postIdCheck.enqueue(object : Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e("Id Check error","아이디 중복 체크 에러")
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful){

                        toast("사용가능한 아이디입니다")
                    }

                    else{
                        toast("아이디를 다시 입력해 주세요")
                    }
                }
            })
        }

    }


    private fun PostUserSignUpResponse() {

        //edittext에 있는 값 받기


        if (et_ac_sign_up_id.text.toString().isNotEmpty() && et_ac_sign_up_pw.text.toString().isNotEmpty()
            && et_ac_sign_up_pwc.text.toString().isNotEmpty() && et_ac_sign_up_nick.text.isNotEmpty()
        ) {

            val input_id: String = et_ac_sign_up_id.text.toString()
            val input_pw: String = et_ac_sign_up_pw.text.toString()
            val input_nickname: String = et_ac_sign_up_nick.text.toString()
            val input_repw: String = et_ac_sign_up_pwc.text.toString()

            RescatApplication.preference.nickname = input_nickname

            //통신 시작
            val postSignUpResponse: Call<PostUserSignUpResponse> =
                networkService.postUserSignUp(PostUserSignUp(input_id, input_pw, input_repw, input_nickname))
            postSignUpResponse.enqueue(object : Callback<PostUserSignUpResponse> {
                override fun onFailure(call: Call<PostUserSignUpResponse>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())

                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(
                    call: Call<PostUserSignUpResponse>,
                    response: Response<PostUserSignUpResponse>
                ) {
                    if (response.isSuccessful) {


                        var token: String = response.body()!!.token
                        Log.v("Sign Up Success", "토큰 + " + token)
                        startActivity<MainActivity>()
                    } else {
                        var message: String = response.body()!!.message
                        Log.e("signup error","회원가입 에러"+message)
                    }
                }
            })
        }
        else{
            toast("빈칸을 다 채워주세요")
        }

    }
}

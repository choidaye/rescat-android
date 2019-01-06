package com.rescat.rescat_android.ui.activity.sign

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import android.util.Log
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
    }


    private fun PostLoginResponse() {

        val input_login_id: String = et_ac_sign_in_id.text.toString()
        val input_login_pw: String = et_ac_sign_in_pw.text.toString()


        val postLoginResponse: Call<PostUserLoginResponse> =
            networkService.postUserLogin(PostUserLogin(input_login_id, input_login_pw))

        postLoginResponse.enqueue(object : Callback<PostUserLoginResponse> {
            override fun onFailure(call: Call<PostUserLoginResponse>, t: Throwable) {
                Log.e("Sign In Fail", t.toString())

            }

            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostUserLoginResponse>, response: Response<PostUserLoginResponse>) {
                if (response.isSuccessful){
                    var token : String = response.body()!!.token
                    Log.v("Sign In Success", "토큰 + " + token)
                    startActivity<MainActivity>()
                }else{
                    var message : String = response.body()!!.message
                    toast(message)
                }
            }
        })
    }

}

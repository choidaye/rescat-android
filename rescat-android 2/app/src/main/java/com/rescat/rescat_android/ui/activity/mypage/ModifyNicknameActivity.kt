package com.rescat.rescat_android.ui.activity.mypage

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rescat.rescat_android.Put.Response.PutMyInfoModifyResponse

import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.sign.SignUpActivity
import kotlinx.android.synthetic.main.activity_modify_nickname.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyNicknameActivity : AppCompatActivity() {

    val requestCode = 1107

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    internal var modifyValue:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_nickname)

        setOnCLickListener()
    }

    private fun setOnCLickListener() {

        btn_ac_modify_my_nickname_ok.setOnClickListener {
            //var modify_nickname: String = et_ac_modify_my_nickname.text.toString()
            PutMyInfoMoædify()

        }

    }

    private fun PutMyInfoMoædify() {


        Log.e("response","리스폰스 들어옴")
        //edittext에 있는 값 받기
        val modify_nickname : String = et_ac_modify_my_nickname.text.toString()

        //토큰


        //통신 시작
        val putMyInfoModifyResponse : Call<PutMyInfoModifyResponse> =
           networkService.putMyNicknameModify(modify_nickname)
        putMyInfoModifyResponse.enqueue(object : Callback<PutMyInfoModifyResponse>{

            override fun onFailure(call: Call<PutMyInfoModifyResponse>, t: Throwable) {
                Log.e("modify error",t.toString())
            }

            override fun onResponse(call: Call<PutMyInfoModifyResponse>, response: Response<PutMyInfoModifyResponse>) {

                if (response.isSuccessful) {
                    val intent : Intent = Intent()
                    intent.putExtra("nickname", "nickname")
                    setResult(Activity.RESULT_OK, intent)

                    Log.e("modifynick","닉네임 바꾸기 통신 성공")
                }

            }
        })


    }


}

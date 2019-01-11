package com.rescat.rescat_android.ui.activity.caretakerAuth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.rescat.rescat_android.Post.Response.CareTakerAuthResponse
import com.rescat.rescat_android.Post.Response.CareTakerMobileAuthResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by gominju on 07/01/2019.
 */

class CareTakerMobileAuthActivity : AppCompatActivity() {
    private var iv_prev: ImageView? = null
    private var tv_next: TextView? = null
    private var btn_next: Button? = null
    private var btn_push_sms: Button? = null
    private var et_name: EditText? = null
    private var et_phone: EditText? = null
    private var et_code: EditText? = null
    private var networkService: NetworkService? = null
    private var phoneNumber: String? = null
    private var userInputCode: String? = null
    private var authCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_taker_mobile_auth)

        init()

        iv_prev!!.setOnClickListener {
            val intent = Intent(applicationContext, CareTakerAuthMainActivity::class.java)
            startActivity(intent)
        }

        tv_next!!.setOnClickListener {
            if (checkEmpty()) {
                checkCodeValidation()
            } else {
                Log.i("minjuLog", "checkempty false")
            }
        }

        btn_next!!.setOnClickListener {
            if (checkEmpty()) {
                checkCodeValidation()
            } else {
                Log.i("minjuLog", "checkempty false")
            }
        }

        btn_push_sms!!.setOnClickListener {
            if (!et_phone!!.text.toString().isEmpty()) {
                phoneNumber = et_phone!!.text.toString()
                getAuthCode()
                et_code!!.requestFocus()
            } else {
                Toast.makeText(this@CareTakerMobileAuthActivity, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getAuthCode() {
        val codeCheckCall = networkService!!.getCareTakerAuthCode(phoneNumber!!)
        codeCheckCall.enqueue(object : Callback<CareTakerMobileAuthResponse> {
            override fun onResponse(call: Call<CareTakerMobileAuthResponse>, response: Response<CareTakerMobileAuthResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    authCode = res!!.code
                } else {
                    Log.i("minjuLog", "response err: " + response.toString())
                }
            }

            override fun onFailure(call: Call<CareTakerMobileAuthResponse>, t: Throwable) {
                Log.i("minjuLog", "response fail " + t.message.toString())
            }
        })
    }

    private fun checkCodeValidation() {
        userInputCode = et_code!!.text.toString()
        if (userInputCode == authCode) {
            val intent = Intent(applicationContext, CareTakerAreaAuthActivity::class.java)
            val editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            editor.putString("caretaker_name", et_name!!.text.toString())
            editor.putString("caretaker_phone", et_phone!!.text.toString())
            editor.commit()
            startActivity(intent)
        } else {
            Toast.makeText(this@CareTakerMobileAuthActivity, "인증 코드가 틀렸습니다", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkEmpty(): Boolean {
        if (et_name!!.text.toString().isEmpty() || et_code!!.text.toString().isEmpty() || et_phone!!.text.toString().isEmpty()) {
            Toast.makeText(this@CareTakerMobileAuthActivity, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    private fun init() {
        iv_prev = findViewById(R.id.iv_caretaker_auth_prev)
        tv_next = findViewById(R.id.tv_caretaker_auth_next)
        et_name = findViewById(R.id.et_caretaker_auth_name)
        et_phone = findViewById(R.id.et_caretaker_auth_phone)
        et_code = findViewById(R.id.et_caretaker_auth_code)
        btn_next = findViewById(R.id.btn_caretaker_mobile_next)
        btn_push_sms = findViewById(R.id.btn_caretaker_auth_sms)
        networkService = RescatApplication.instance.networkService
    }
}

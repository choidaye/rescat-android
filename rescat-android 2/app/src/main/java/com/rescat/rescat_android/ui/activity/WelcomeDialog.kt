package com.rescat.rescat_android.ui.activity

/**
 * Created by gominju on 08/01/2019.
 */

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button

import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.caretakerAuth.CareTakerAuthMainActivity

class WelcomeDialog(context: Context?) : Dialog(context) {
    private var btn_left: Button? = null
    private var btn_right: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 다이얼로그 외부 화면 흐리게 표현
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow
        requestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀 바 삭제
        setContentView(R.layout.dialog_welcome_caretaker_auth)

        btn_left = findViewById(R.id.btn_welcome_left)
        btn_right = findViewById(R.id.btn_welcome_right)

        btn_left!!.setOnClickListener { dismiss() }

        btn_right!!.setOnClickListener {
            val intent = Intent(context, CareTakerAuthMainActivity::class.java)
            context.startActivity(intent)
        }
    }


}

package com.rescat.rescat_android.ui.activity.caretakerAuth

/**
 * Created by gominju on 08/01/2019.
 */

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button

import com.rescat.rescat_android.R

class CustomDialog(context: Context?) : Dialog(context) {
    private var btn_finish: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 다이얼로그 외부 화면 흐리게 표현
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow
        requestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀 바 삭제
        setContentView(R.layout.dialog_caretaker_auth_finish)

        btn_finish = findViewById(R.id.btn_finish)

        btn_finish!!.setOnClickListener { dismiss() }
    }


}

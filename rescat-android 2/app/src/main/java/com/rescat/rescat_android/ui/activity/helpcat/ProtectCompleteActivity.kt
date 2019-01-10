package com.rescat.rescat_android.ui.activity.helpcat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_adopt_complete.*

class ProtectCompleteActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_complete)

        setInitLayout()
        setButtonListener()
    }

    private fun setInitLayout() {
        text_help_complete_title.text="임시보호 신청"
        text_help_complete_header_desc.text="임시보호 신청이 완료되었습니다!\n당신의 아름다운 결정을 지지합니다!"
    }

    private fun setButtonListener() {
        btn_adopt_apply_exit.setOnClickListener{
            finish()
        }

        btn_go_home.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}

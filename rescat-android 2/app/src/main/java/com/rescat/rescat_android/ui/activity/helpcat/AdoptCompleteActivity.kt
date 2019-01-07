package com.rescat.rescat_android.ui.activity.helpcat

import android.app.Activity
import android.os.Bundle
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_adopt_complete.*

class AdoptCompleteActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_complete)

        setInitLayout()
        setButtonListener()
    }

    private fun setInitLayout() {
        text_help_complete_title.text="입양신청"
        text_help_complete_header_desc.text="입양 신청이 완료되었습니다!\n당신의 아름다운 결정을 지지합니다!"
    }

    private fun setButtonListener() {
        btn_adopt_apply_exit.setOnClickListener{
            finish()
        }
    }


}

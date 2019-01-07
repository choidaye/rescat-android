package com.rescat.rescat_android.ui.activity.helpcat

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_adopt_apply.*




class ProtectApplyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_apply)

        setInitLayout()
        setbtnListener()


    }

    private fun setInitLayout() {
        text_help_apply_title.text = "임시보호 신청"
        text_help_apply_header_desc.text = "임시보호를 하시나요?\n당신의 아름다운 결심을 지지합니다."

    }

    private fun setbtnListener(){
        btn_adopt_complete.setOnClickListener {
            show()
        }

        btn_adopt_apply_back.setOnClickListener{
            finish()
        }

    }

    fun show() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("정말 입양 신청하시겠습니까?")
        builder.setMessage("한 생명을 끝까지 책임진다는 \n신중한 마음으로 결정을 내려주세요.")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("확인") { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("취소") { dialog, which ->
            val intent: Intent = Intent(this, ProtectCompleteActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()

        dialog.show()

        // Get the alert dialog buttons reference
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor("#FF6e4d37"))

        negativeButton.setTextColor(Color.parseColor("#FFf29191"))
    }
}

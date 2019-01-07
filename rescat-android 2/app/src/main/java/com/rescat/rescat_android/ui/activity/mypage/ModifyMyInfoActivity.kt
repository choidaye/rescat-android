package com.rescat.rescat_android.ui.activity.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_modify_my_info.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class ModifyMyInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_my_info)

        setOnCLickListener()
    }

    private fun setOnCLickListener() {

        btn_ac_modify_my_info_nickname.setOnClickListener {

            val intent = Intent(this@ModifyMyInfoActivity, ModifyNicknameActivity::class.java)
//            Log.v("TAG", "초대3화면 플젝넘버 = $project_idx")
            intent.putExtra("nickname","nickname")
            startActivity(intent)
        }

    }


}

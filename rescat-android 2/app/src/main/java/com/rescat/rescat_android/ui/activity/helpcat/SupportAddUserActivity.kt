package com.rescat.rescat_android.ui.activity.helpcat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_support_add_user.*

class SupportAddUserActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_add_user)

        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {
        btn_support_add_ok.setOnClickListener {
            
        }
    }
}

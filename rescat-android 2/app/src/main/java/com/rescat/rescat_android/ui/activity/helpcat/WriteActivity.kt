package com.rescat.rescat_android.ui.activity.helpcat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.startActivity

class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {

        btn_ac_write_back.setOnClickListener {
            finish()
        }

        btn_write_adopt.setOnClickListener {

        }

        btn_write_support.setOnClickListener {
            startActivity<SupportAddActivity>()

        }

    }



}

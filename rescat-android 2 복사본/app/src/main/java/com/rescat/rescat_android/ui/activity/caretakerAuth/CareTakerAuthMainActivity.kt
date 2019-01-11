package com.rescat.rescat_android.ui.activity.caretakerAuth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rescat.rescat_android.R

class CareTakerAuthMainActivity : AppCompatActivity() {

    private var btn_next: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_taker_auth_main)

        btn_next = findViewById(R.id.btn_caretaker_auth_main_next)

        btn_next!!.setOnClickListener {
            val intent = Intent(applicationContext, CareTakerMobileAuthActivity::class.java)
            startActivity(intent)
        }
    }
}

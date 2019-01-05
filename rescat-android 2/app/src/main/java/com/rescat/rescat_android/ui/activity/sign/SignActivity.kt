package com.rescat.rescat_android.ui.activity.sign

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_sign.*
import org.jetbrains.anko.startActivity

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        btn_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }

        btn_login_go.setOnClickListener {
            startActivity<SignInActivity>()
        }
    }
}

package com.rescat.rescat_android.ui.activity.mypage

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R

class ModifyPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)
    }
}
=======
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rescat.rescat_android.R

class ModifyPasswordActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_pw)

        setOnCLickListener()
    }

    private fun setOnCLickListener() {

    }


}
>>>>>>> 6d31a015b535a8b76e0e7853ecae41a71bc355ce

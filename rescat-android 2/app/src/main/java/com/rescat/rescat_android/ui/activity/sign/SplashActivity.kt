package com.rescat.rescat_android.ui.activity.sign

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.rescat.rescat_android.R
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val iv = findViewById(R.id.imageView2) as ImageView
        //iv.setImageResource(R.drawable.img);
        Glide.with(this).load(R.drawable.splash).into(iv)

        Handler().postDelayed({


            startActivity<SignActivity>()

        }, 2000)


    }


}

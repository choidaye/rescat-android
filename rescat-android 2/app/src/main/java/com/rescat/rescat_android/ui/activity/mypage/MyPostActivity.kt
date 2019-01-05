package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.MyPostTabAdapter
import kotlinx.android.synthetic.main.activity_adopt.*
import kotlinx.android.synthetic.main.activity_my_post.*

class MyPostActivity : AppCompatActivity() {

    lateinit var myposttabAdapter: MyPostTabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        setTabLayout()
    }

    private fun setTabLayout() {
        myposttabAdapter = MyPostTabAdapter(supportFragmentManager)
        vp_my_post_fragment_container.adapter = myposttabAdapter

        tab_my_post.setupWithViewPager(vp_my_post_fragment_container)
    }


}

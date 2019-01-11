package com.rescat.rescat_android.ui.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.mypage.MyLocationActivity
import com.rescat.rescat_android.ui.activity.mypage.MyPostActivity
import com.rescat.rescat_android.ui.activity.mypage.NoticeActivity
import kotlinx.android.synthetic.main.fragment_my_page_member.*
import org.jetbrains.anko.support.v4.startActivity

class MyPageMemberFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnCLickListener()
    }

    private fun setOnCLickListener() {
        btn_fg_my_page_go_notice.setOnClickListener {
            startActivity<NoticeActivity>()
        }

        btn_ll_my_page_setting_location.setOnClickListener {
            startActivity<MyLocationActivity>()
        }

        btn_my_page_my_post.setOnClickListener {
            startActivity<MyPostActivity>()
        }
    }




}
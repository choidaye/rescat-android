package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.NoticeData
import com.rescat.rescat_android.model.SearchData
import com.rescat.rescat_android.ui.adapter.NoticeRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.activity_search.*

class NoticeActivity : AppCompatActivity() {

    lateinit var noticeRecyclerViewAdapter: NoticeRecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)


        setOnCLickListener()
        setRecyclerView()
    }

    private fun setOnCLickListener() {

    }


    private fun setRecyclerView() {

        var dataList: ArrayList<NoticeData> = ArrayList()
        dataList.add(NoticeData(1,"제목","1시간 전","알림에 대한 내용이 들어갑니다"))
        dataList.add(NoticeData(2,"제목","1시간 전","알림에 대한 내용이 들어갑니다"))
        dataList.add(NoticeData(3,"제목","1시간 전","알림에 대한 내용이 들어갑니다"))

        noticeRecyclerViewAdapter = NoticeRecyclerViewAdapter(this, dataList)
        rv_ac_notice_list.adapter = noticeRecyclerViewAdapter
        rv_ac_notice_list.layoutManager = LinearLayoutManager(this)
    }

}

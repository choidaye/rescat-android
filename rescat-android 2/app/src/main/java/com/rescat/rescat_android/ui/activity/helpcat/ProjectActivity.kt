package com.rescat.rescat_android.ui.activity.helpcat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.ProjectTabAdapter
import com.rescat.rescat_android.ui.adapter.ProtectTabAdapter
import kotlinx.android.synthetic.main.activity_adopt.*
import kotlinx.android.synthetic.main.activity_support.*


class ProjectActivity : AppCompatActivity() {

    lateinit var tabAdapter: ProjectTabAdapter
    var idx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        setTabLayout()
        setInitLayout()
        setBackButtonListener()
    }

    private fun setBackButtonListener() {
        btn_back.setOnClickListener {
            finish()
        }
    }


    private fun setInitLayout() {
        text_support_fragment_title.text = "프로젝트 후원"
    }

    private fun setTabLayout() {
        val intent = intent
        idx = intent.getIntExtra("idx", 0)
        tabAdapter = ProjectTabAdapter(supportFragmentManager, idx)
        vp_support_project_fragment_container.adapter = tabAdapter

        tab_support.setupWithViewPager(vp_support_project_fragment_container)
    }


}

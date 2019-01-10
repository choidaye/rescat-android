package com.rescat.rescat_android.ui.activity.helpcat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.MedicalTabAdapter
import kotlinx.android.synthetic.main.activity_adopt.*


import kotlinx.android.synthetic.main.activity_support.*

class MedicalActivity : AppCompatActivity() {

    var idx: Int = 0
       lateinit var tabAdapter:  MedicalTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        setInitLayout()
        setTabLayout()

        setBackButtonListener()
    }

    private fun setBackButtonListener() {
        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun setInitLayout() {
        text_support_fragment_title.text= "치료비 후원"

    }

    private fun setTabLayout() {
        val intent = intent
        idx = intent.getIntExtra("idx", 0)
        tabAdapter = MedicalTabAdapter(supportFragmentManager, idx)
        vp_support_project_fragment_container.adapter = tabAdapter

        tab_support.setupWithViewPager(vp_support_project_fragment_container)
    }



}
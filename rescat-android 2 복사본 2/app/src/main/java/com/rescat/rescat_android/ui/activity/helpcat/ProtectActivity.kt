package com.rescat.rescat_android.ui.activity.helpcat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.ProtectTabAdapter
import kotlinx.android.synthetic.main.activity_adopt.*

class ProtectActivity : AppCompatActivity() {

    lateinit var tabAdapter: ProtectTabAdapter
    var idx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt)

        setInitLayout()
        setTabLayout()
        setBackButtonListener()
    }

    private fun setInitLayout() {
        text_help_fragment_title.text = "임시 보호"
    }

    private fun setTabLayout() {
        val intent = intent
        idx = intent.getIntExtra("idx", 0)
        tabAdapter = ProtectTabAdapter(supportFragmentManager, idx)
        vp_adopt_fragment_container.adapter = tabAdapter

        tab_adopt.setupWithViewPager(vp_adopt_fragment_container)
    }

    private fun setBackButtonListener() {
        btn_adopt_back.setOnClickListener {
            finish()
        }
    }
}

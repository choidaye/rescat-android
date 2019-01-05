package com.rescat.rescat_android.ui.activity.helpcat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.AdoptTabAdapter
import com.rescat.rescat_android.ui.fragment.helpcat.adopt.AdoptInfoFragment
import kotlinx.android.synthetic.main.activity_adopt.*

class AdoptActivity : AppCompatActivity() {

    lateinit var tabAdapter: AdoptTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt)

        setTabLayout()
    }

    private fun setTabLayout() {
        tabAdapter = AdoptTabAdapter(supportFragmentManager)
        vp_adopt_fragment_container.adapter = tabAdapter

        tab_adopt.setupWithViewPager(vp_adopt_fragment_container)
    }
}

package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.RegionData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MyLocationRecyclerViewAdapter
import com.rescat.rescat_android.ui.adapter.MySupportRecyclcerViewAdapter
import kotlinx.android.synthetic.main.activity_my_support.*

class SettingLocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_location)

    }


}

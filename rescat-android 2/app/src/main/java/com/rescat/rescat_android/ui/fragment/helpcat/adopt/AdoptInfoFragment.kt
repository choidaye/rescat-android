package com.rescat.rescat_android.ui.fragment.helpcat.adopt

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.adapter.AdoptInfoBannerAdapter
import kotlinx.android.synthetic.main.fragment_adopt_info.*

class AdoptInfoFragment : Fragment() {

    lateinit var infoBannerAdapter: AdoptInfoBannerAdapter

    var data: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setViewPager()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adopt_info, container, false)
    }

    private fun setViewPager() {
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)

        infoBannerAdapter = AdoptInfoBannerAdapter(data)
        vp_adopt_info_banner.adapter = infoBannerAdapter
    }

}

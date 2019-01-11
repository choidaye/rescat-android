package com.rescat.rescat_android.ui.adapter

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R

class HomeMainBannerAdapter(val data: ArrayList<Int>): PagerAdapter() {
    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as CardView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_home_main_banner, container, false)
        setupData(view)

        if (container != null) {
            container!!.addView(view)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (container != null) {
            container!!.removeView(`object` as CardView)
        }
    }

    private fun setupData(view: View) {
        val image: ImageView = view.findViewById(R.id.image_home_main_banner)
        val title: TextView = view.findViewById(R.id.text_home_main_banner_title)
        title.text = "길고양이 겨울나기 프로젝트"
    }
}
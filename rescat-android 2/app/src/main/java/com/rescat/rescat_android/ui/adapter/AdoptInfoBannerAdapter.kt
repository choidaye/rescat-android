package com.rescat.rescat_android.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R

class AdoptInfoBannerAdapter(val data: ArrayList<Int>): PagerAdapter() {

    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_adopt_info_banner, container, false)
        setupData(view)

        if (container != null) {
            container!!.addView(view)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (container != null) {
            container!!.removeView(`object` as ConstraintLayout)
        }
    }

    private fun setupData(view: View) {
        val image: ImageView = view.findViewById(R.id.image_adopt_info_banner)
//        val content: TextView = view.findViewById(R.id.text_adopt_info_content)
//        val name: TextView = view.findViewById(R.id.text_adopt_info_name)
//        val age: TextView = view.findViewById(R.id.text_adopt_info_age)
//        val gender: TextView = view.findViewById(R.id.text_adopt_info_gender)
//        val kind: TextView = view.findViewById(R.id.text_adopt_info_kind)
//        val tnr: TextView = view.findViewById(R.id.text_adopt_info_tnr)
//        val vaccination: TextView = view.findViewById(R.id.text_adopt_info_vaccination)
//        val uniqueness: TextView = view.findViewById(R.id.text_adopt_info_uniqueness)
//
//        content.text = "안경을 쓰고 있는 애교 많은 고양이 레옹,\n새로운 집사를 기다립니다."
//
//        name.text = "레옹"
//        age.text = "2살"
//        gender.text = "남아"
//        kind.text = "믹스"
//        tnr.text = "안함"
//        vaccination.text = "모름"
//
//        uniqueness.text = "산책과 풀을 좋아하는 산책냥이입니다."
    }
}
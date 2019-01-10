package com.rescat.rescat_android.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.PhotoData

class MedicalInfoBannerAdapter(val data: ArrayList<PhotoData>): PagerAdapter() {

    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_medical_info_banner, container, false)
        setupData(view, position)

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

    private fun setupData(view: View, position: Int) {
        val image: ImageView = view.findViewById(R.id.image_medical_info_banner)
        Glide.with(view.context).load(data[position].url).into(image)

    }


}
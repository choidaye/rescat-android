package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.Get.GetMyNoticeResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.RegionData
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import org.jetbrains.anko.startActivity


class MyPageLocationAdapter(val ctx: Context, val regionList: ArrayList<RegionData>) : RecyclerView.Adapter<MyPageLocationAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageLocationAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_address, parent, false)


        return Holder(view)
    }

    override fun getItemCount(): Int = regionList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = regionList[position].name


    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tv_fg_my_location_address) as TextView

    }

}
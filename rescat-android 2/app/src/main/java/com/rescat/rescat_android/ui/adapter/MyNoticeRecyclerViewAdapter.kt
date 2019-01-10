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
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import org.jetbrains.anko.startActivity


class MyNoticeRecyclerViewAdapter(val ctx: Context, val noticelist: ArrayList<GetMyNoticeResponse>) : RecyclerView.Adapter<MyNoticeRecyclerViewAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  MyNoticeRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_notice, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = noticelist.size

    override fun onBindViewHolder(holder:  MyNoticeRecyclerViewAdapter.Holder, position: Int) {
        holder.contents.text = noticelist[position].notification.contents
        holder.creatAt.text = noticelist[position].createdAt
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val creatAt: TextView = itemView.findViewById(R.id.tv_rv_item_notice_date) as TextView
        val contents : TextView = itemView.findViewById(R.id.tv_rv_item_notice_content) as TextView

    }


}
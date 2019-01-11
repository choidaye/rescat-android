package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.NoticeData


class NoticeRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<NoticeData>) : RecyclerView.Adapter<NoticeRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_notice, parent, false)


        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].n_title
        holder.contents.text = dataList[position].n_contents
        holder.date.text = dataList[position].n_date

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_rv_item_notice_title) as TextView
        val contents : TextView = itemView.findViewById(R.id.tv_rv_item_notice_content) as TextView
        val date : TextView = itemView.findViewById(R.id.tv_rv_item_notice_date) as TextView

    }

}
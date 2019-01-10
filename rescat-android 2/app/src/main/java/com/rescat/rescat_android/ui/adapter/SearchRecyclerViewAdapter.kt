package com.rescat.rescat_android.ui.adapter

import android.content.Context

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.SearchData

class SearchRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<SearchData>) : RecyclerView.Adapter<SearchRecyclerViewAdapter.Holder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_result, parent, false)



        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].s_title
        holder.category.text = dataList[position].s_category

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_rv_item_search_title) as TextView
        val category : TextView = itemView.findViewById(R.id.tv_rv_item_search_category) as TextView
    }


}
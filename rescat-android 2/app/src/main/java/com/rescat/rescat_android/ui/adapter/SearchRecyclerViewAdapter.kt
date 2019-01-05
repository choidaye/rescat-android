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

//    private lateinit var onItemClick : View.OnClickListener
//
//    fun setOnItemClickListener(I : View.OnClickListener)
//    {
//        onItemClick = I
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_result, parent, false)

        //아이템클릭 메소드
//        view.setOnClickListener(onItemClick)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].s_title
        holder.category.text = dataList[position].s_category



        val requestOptions = RequestOptions()
//        requestOptions.placeholder(R.drawable.기본적으로 띄울 이미지)
//        requestOptions.error(R.drawable.에러시 띄울 이미지)
//        requestOptions.override(150)            //사진의 크기를 줄일 수 있음

//
//        Glide.with(ctx)
//            .setDefaultRequestOptions(requestOptions)
//            .load(dataList[position].s_photo)
//            .thumbnail(0.5f)
//            .into(holder.image)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_rv_item_search_title) as TextView
        val category : TextView = itemView.findViewById(R.id.tv_rv_item_search_category) as TextView
    }


}
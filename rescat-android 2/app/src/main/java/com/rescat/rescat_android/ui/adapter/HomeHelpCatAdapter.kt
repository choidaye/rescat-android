package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rescat.rescat_android.R

class HomeHelpCatAdapter(val data : ArrayList<Int>) : RecyclerView.Adapter<HomeHelpCatAdapter.Holder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_help_cat, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = "레옹"
        holder.content.text = "얼굴에 있는 얼룩이 특징인 레옹. 새로운 주인을 기다려요."
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById((R.id.text_home_help_cat_name))
        val content : TextView = itemView.findViewById(R.id.text_home_help_cat_content)
    }
}
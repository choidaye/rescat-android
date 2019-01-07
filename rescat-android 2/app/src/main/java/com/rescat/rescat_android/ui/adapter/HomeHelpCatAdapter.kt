package com.rescat.rescat_android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.HelpCardData

class HomeHelpCatAdapter(val data : ArrayList<HelpCardData>) : RecyclerView.Adapter<HomeHelpCatAdapter.Holder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_help_cat, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = data[position].name
        holder.content.text = data[position].contents
        if(data[position].type == 0) {
            holder.type.setImageResource(R.drawable.img_tag_1)
        } else {
            holder.type.setImageResource(R.drawable.img_tag_2)
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById((R.id.text_home_help_cat_name))
        val content : TextView = itemView.findViewById(R.id.text_home_help_cat_content)
        val type : ImageView = itemView.findViewById(R.id.image_help_cat_tag)
    }
}
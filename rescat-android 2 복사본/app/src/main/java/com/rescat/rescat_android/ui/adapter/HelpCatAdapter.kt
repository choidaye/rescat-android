package com.rescat.rescat_android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.HelpCardData
import com.rescat.rescat_android.utils.ConvertData

class HelpCatAdapter(val data: ArrayList<HelpCardData>,
                     val itemClick: (Int) -> Unit): RecyclerView.Adapter<HelpCatAdapter.Holder>() {

    override fun getItemCount(): Int = data.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_help_cat, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
        Glide.with(holder.itemView.context).load(data[position].photo.url).into(holder.image)
        holder.name.text = data[position].name
        holder.content.text = data[position].contents
        holder.views.text = (data[position].viewCount).toString()
        holder.time.text = ConvertData.convertDateToString(data[position].updatedAt)
        holder.viewCount.text = "조회 " + data[position].viewCount + "회"
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_help_cat)
        val name: TextView = itemView.findViewById(R.id.text_help_cat_name)
        val content: TextView = itemView.findViewById(R.id.text_help_cat_content)
        val views: TextView = itemView.findViewById(R.id.text_help_cat_views)
        val time: TextView = itemView.findViewById(R.id.text_help_cat_time)
        val viewCount: TextView = itemView.findViewById(R.id.text_help_cat_views)

        fun bind(position: Int) {
            itemView.setOnClickListener { itemClick(data[position].idx) }
        }
    }
}
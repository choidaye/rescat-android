package com.rescat.rescat_android.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.Get.GetMainPageFunding
import com.rescat.rescat_android.R

class HomeFundCardAdapter(val data: ArrayList<GetMainPageFunding>): RecyclerView.Adapter<HomeFundCardAdapter.Holder>() {

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_fund_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = data[position].title
        if(data[position].category == 0) {
            holder.category.setImageResource(R.drawable.img_tag_1)
        } else {
            holder.category.setImageResource(R.drawable.img_tag_2)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_home_fund_card)
        val title: TextView = itemView.findViewById(R.id.text_home_fund_card_title)
        val entire_percent_view: View = itemView.findViewById(R.id.view_home_fund_card_entire_percent)
        val percent_view: View = itemView.findViewById(R.id.view_home_fund_card_percent)
        val percent: TextView = itemView.findViewById(R.id.text_home_fund_card_percent)
        val remain: TextView = itemView.findViewById(R.id.text_home_fund_card_remain)
        val category : ImageView = itemView.findViewById(R.id.image_home_fund_card_tag)

        fun bind(data: Int) {
            (percent_view.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.4f
        }
    }
}
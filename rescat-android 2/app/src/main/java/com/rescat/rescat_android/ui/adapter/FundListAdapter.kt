package com.rescat.rescat_android.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R

class FundListAdapter(val data: ArrayList<Int>): RecyclerView.Adapter<FundListAdapter.Holder>() {
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_fund, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_my_post_fund)
        val title: TextView = itemView.findViewById(R.id.tv_fund_list_title)
        val content: TextView = itemView.findViewById(R.id.text_fund_list_content)
        val entire_percent_view: View = itemView.findViewById(R.id.view_fund_list_entire_percent)
        val percent_view: View = itemView.findViewById(R.id.view_fund_list_percent)
        val price: TextView = itemView.findViewById(R.id.text_fund_list_price)
        val percent: TextView = itemView.findViewById(R.id.text_fund_list_percent)
        val remain: TextView = itemView.findViewById(R.id.text_fund_list_remain)

        fun bind(data: Int) {
            (percent_view.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.8f
        }
    }
}
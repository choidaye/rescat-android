package com.rescat.rescat_android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rescat.rescat_android.Get.GetFundingResponse
import com.rescat.rescat_android.R


class SupportAdapter(val data: ArrayList<GetFundingResponse>,
                     val itemClick: (Int) -> Unit): RecyclerView.Adapter<SupportAdapter.Holder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_fund, parent, false)
        return Holder(view)
    }


    override fun onBindViewHolder(holder: SupportAdapter.Holder, position: Int) {
        holder.bind(position)
        Glide.with(holder.itemView.context).load(data[position].mainPhoto.url).into(holder.image)
        holder.title.text = data[position].title
        holder.introduction.text = data[position].introduction
        holder.limitAt.text = data[position].limitAt
        holder.currentAmount.text = data[position].currentAmount.toString()

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_fund)
        val title: TextView = itemView.findViewById(R.id.tv_fund_list_title)
        val introduction: TextView = itemView.findViewById(R.id.text_fund_list_content)
        val entire_percent_view: View = itemView.findViewById(R.id.view_fund_list_entire_percent)
        val percent_view: View = itemView.findViewById(R.id.view_fund_list_percent)
        val currentAmount: TextView = itemView.findViewById(R.id.text_fund_list_price)
        val percent: TextView = itemView.findViewById(R.id.text_fund_list_percent)
        val limitAt: TextView = itemView.findViewById(R.id.text_fund_list_remain)

        fun bind(position: Int) {
            itemView.setOnClickListener { itemClick(data[position].idx) }
        }

//        fun bind(data: Int) {
//            (percent_view.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.8f
//        }
    }




}
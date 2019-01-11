package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.SupportActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.Get.GetMainPageFunding
import com.rescat.rescat_android.R
import com.rescat.rescat_android.R.id.*
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import com.rescat.rescat_android.ui.activity.helpcat.MedicalActivity
import com.rescat.rescat_android.ui.fragment.helpcat.FundFragment
import org.jetbrains.anko.startActivity

class HomeFundCardAdapter(val ctx: Context, val data: ArrayList<GetMainPageFunding>) : RecyclerView.Adapter<HomeFundCardAdapter.Holder>() {

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_fund_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
        holder.title.text = data[position].title

        val requestOptions = RequestOptions()
       requestOptions.placeholder(R.drawable.img_default_2)
//        requestOptions.error(R.drawable.에러시 띄울 이미지)
//        requestOptions.override(150)

        if(data[position].category == 0) {
            holder.category.setImageResource(R.drawable.img_tag_1)
        } else {
            holder.category.setImageResource(R.drawable.img_tag_2)
        }



//        holder.clickitem.setOnClickListener {
//            ctx.startActivity<MedicalActivity>("idx" to data[position].idx)
//
//        }




    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_home_fund_card)
        val title: TextView = itemView.findViewById(R.id.text_home_fund_card_title)
        val entire_percent_view: View = itemView.findViewById(R.id.view_home_fund_card_entire_percent)
        val percent_view: View = itemView.findViewById(R.id.view_home_fund_card_percent)
        val percent: TextView = itemView.findViewById(R.id.text_home_fund_card_percent)
        val remain: TextView = itemView.findViewById(R.id.text_home_fund_card_remain)
        val category : ImageView = itemView.findViewById(R.id.image_home_fund_card_tag)
       // val clickitem : CardView = itemView.findViewById(R.id.rv_item_home_fund_card) as CardView





        fun bind(data: Int) {
            (percent_view.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.4f
        }
    }
}
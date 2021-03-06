package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.Get.GetMyPageFundResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import org.jetbrains.anko.startActivity


//GetMyPageFundResponse

class MyPostFundRecyclerViewAdapter(val ctx: Context, val mypostfundList: ArrayList<GetMyPageFundResponse>,var requestManager: RequestManager) : RecyclerView.Adapter<MyPostFundRecyclerViewAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostFundRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_post_fund, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = mypostfundList.size

    override fun onBindViewHolder(holder: MyPostFundRecyclerViewAdapter.Holder, position: Int) {

        requestManager.load(mypostfundList[position].photos).into(holder.photo)
        holder.title.text = mypostfundList[position].title
        holder.contents.text = mypostfundList[position].contents
        holder.limitAt.text = mypostfundList[position].limitAt
        holder.category.inputType = mypostfundList[position].category


        val requestOptions = RequestOptions()
       requestOptions.placeholder(R.drawable.img_default_2)
//        requestOptions.error(R.drawable.에러시 띄울 이미지)
//        requestOptions.override(150)


        Glide.with(ctx)
            .setDefaultRequestOptions(requestOptions)
            .load(mypostfundList[position].photos)
            .thumbnail(0.5f)
            .into(holder.photo)

        holder.clickitem.setOnClickListener {
            ctx.startActivity<AdoptActivity>("idx" to mypostfundList[position].idx)

            Log.e("item click error","아이템 클릭 실패")

        }




        Log.e("viewholder","뷰홀더 완료")



    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_my_post_fund_list_title) as TextView
        val contents : TextView = itemView.findViewById(R.id.tv_my_post_fund_content) as TextView
        val limitAt : TextView = itemView.findViewById(R.id.tv_my_post_fund_date) as TextView
        val photo : ImageView = itemView.findViewById(R.id.iv_my_post_fund_photo) as ImageView
        val percent : TextView = itemView.findViewById(R.id.tv_my_post_fund_percent) as TextView
        val entire_percent_view : View = itemView.findViewById(R.id.view_my_post_fund_list_entire_percent) as View
        val current_percent_view : View = itemView.findViewById(R.id.view_my_post_fund_list_percent) as View
        val category : TextView = itemView.findViewById(R.id.tv_my_post_fund_type) as TextView
        val clickitem : RelativeLayout = itemView.findViewById(R.id.rv_my_post_care_list_item) as RelativeLayout


    }


}
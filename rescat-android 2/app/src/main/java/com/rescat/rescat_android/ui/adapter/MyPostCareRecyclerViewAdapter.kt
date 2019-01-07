package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.Get.GetMyPageCareResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import org.jetbrains.anko.startActivity

class MyPostCareRecyclerViewAdapter(val ctx: Context, val mypostcarelist: ArrayList<GetMyPageCareResponse>) : RecyclerView.Adapter<MyPostCareRecyclerViewAdapter.Holder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostCareRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_post_care, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = mypostcarelist.size

    override fun onBindViewHolder(holder: MyPostCareRecyclerViewAdapter.Holder, position: Int) {

        Log.e("holder  error","홀더 실패")


        holder.name.text = mypostcarelist[position].name
        holder.contents.text = mypostcarelist[position].contents
        holder.updatedAt.text = mypostcarelist[position].updatedAt
        holder.viewCount.inputType = mypostcarelist[position].viewCount
        holder.type.inputType = mypostcarelist[position].type

        holder.clickitem.setOnClickListener {
            ctx.startActivity<AdoptActivity>("idx" to mypostcarelist[position].idx)

            Log.e("item click error","아이템 클릭 실패")

        }


        val requestOptions = RequestOptions()
        Glide.with(ctx)
            .setDefaultRequestOptions(requestOptions)
            .load(mypostcarelist[position].photos)
            .thumbnail(1.0f)
            .into(holder.photos)

        Log.e("viewholder","뷰홀더 완료")


    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_my_post_care_name) as TextView
        val contents : TextView = itemView.findViewById(R.id.tv_my_post_care_content) as TextView
        val updatedAt : TextView = itemView.findViewById(R.id.tv_my_post_care_time) as TextView
        val viewCount : TextView = itemView.findViewById(R.id.tv_my_post_care_viewcount) as TextView
        val type : TextView = itemView.findViewById(R.id.tv_my_post_care_type) as TextView
        val photos : ImageView = itemView.findViewById(R.id.iv_care_cat_photo) as ImageView
        val clickitem : RelativeLayout = itemView.findViewById(R.id.rv_my_post_cafe_list_item) as RelativeLayout


    }


}
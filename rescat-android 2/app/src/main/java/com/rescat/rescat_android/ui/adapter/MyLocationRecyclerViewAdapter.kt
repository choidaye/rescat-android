package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.RegionData

class MyLocationRecyclerViewAdapter(val ctx: Context, val mylocationlist: ArrayList<RegionData>) : RecyclerView.Adapter<MyLocationRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLocationRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_loction_setting, parent, false)
        return Holder(view)
    }



    override fun getItemCount(): Int = mylocationlist.size

    override fun onBindViewHolder(holder: MyLocationRecyclerViewAdapter.Holder, position: Int) {

        holder.name.text = mylocationlist[position].name


        if (position ==0){
            holder.loction.setBackgroundResource(R.drawable.pink_round_square)
        }

        if(mylocationlist[position].name ==""){
            holder.loction.setBackgroundResource(R.drawable.btn_location_add)
        }

        Log.e("viewholder","뷰홀더 완료")

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_ac_my_location_address) as TextView
        val loction:RelativeLayout = itemView.findViewById(R.id.loction) as RelativeLayout
    }


}
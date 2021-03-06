package com.rescat.rescat_android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rescat.rescat_android.Get.GetFundingCommentResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.utils.ConvertData
import java.util.*


class SupportCommentRecyclerViewAdapter(var dataList: ArrayList<GetFundingCommentResponse>) : RecyclerView.Adapter<SupportCommentRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportCommentRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_support_comment, parent, false)


        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].nickname
        holder.content.text = dataList[position].contents
        holder.date.text = ConvertData.convertDateToString(dataList[position].createdAt)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.text_support_comment_name) as TextView
        val content : TextView = itemView.findViewById(R.id.text_support_comment_content) as TextView
        val date : TextView = itemView.findViewById(R.id.text_support_comment_date) as TextView
    }

}
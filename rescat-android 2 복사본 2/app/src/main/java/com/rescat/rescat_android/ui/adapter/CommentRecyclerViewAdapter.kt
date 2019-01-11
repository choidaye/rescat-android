package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.CommentData
import com.rescat.rescat_android.utils.ConvertData
import java.util.*


class CommentRecyclerViewAdapter(var dataList: ArrayList<CommentData>,
                                 val itemClick: (Int) -> Unit) : RecyclerView.Adapter<CommentRecyclerViewAdapter.Holder>() {
    lateinit var context: Context
    val caretaker = "CARETAKER"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_help_cat_comment, parent, false)
        context = parent.context

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
        holder.name.text = dataList[position].nickname
        holder.content.text = dataList[position].contents
        holder.date.text = ConvertData.convertDateToString(dataList[position].createdAt)
        if (dataList[position].userRole.equals(caretaker)) {
            Log.i("test", "userrole")
            holder.name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_badge_small, 0)
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_badge_small, 0)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.text_help_cat_comment_name) as TextView
        val content: TextView = itemView.findViewById(R.id.text_help_cat_comment_content) as TextView
        val date: TextView = itemView.findViewById(R.id.text_help_cat_comment_date) as TextView

        fun bind(position: Int) {
            itemView.findViewById<ImageView>(R.id.btn_help_cat_comment_more)
                .setOnClickListener { itemClick(dataList[position].idx) }
        }
    }
}
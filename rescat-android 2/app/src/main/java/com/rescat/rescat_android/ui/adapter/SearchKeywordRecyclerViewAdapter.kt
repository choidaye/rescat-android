package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.SearchKeywordData


class SearchKeywordRecyclerViewAdapter(val ctx: Context, val searchKeywordList: ArrayList<SearchKeywordData>) : RecyclerView.Adapter<SearchKeywordRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchKeywordRecyclerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_keyword, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int =  searchKeywordList.size

    fun addNewItem(searchKeywordData: SearchKeywordData){
        val position : Int = searchKeywordList.size

       searchKeywordList.add(searchKeywordData)
        notifyItemInserted(position)
    }


    override fun onBindViewHolder(holder: SearchKeywordRecyclerViewAdapter.Holder, position: Int) {
        holder.keyword.text = searchKeywordList[position].keyword
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyword: TextView = itemView.findViewById(R.id.rv_ac_search_keyword_list) as TextView

    }


}
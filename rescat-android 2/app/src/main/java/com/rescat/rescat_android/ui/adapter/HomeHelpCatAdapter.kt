package com.rescat.rescat_android.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.HelpCardData



class HomeHelpCatAdapter(val data : ArrayList<HelpCardData>,
                         val itemClick: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_FOOTER : Int = 2

    override fun getItemCount(): Int = data.size +1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_FOOTER) {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_help_cat_more, parent, false)
            return FooterHolder(view)
        }else {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_help_cat, parent, false)
            return Holder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) {
            // This is where we'll add footer.
            TYPE_FOOTER
        } else super.getItemViewType(position)

    }
    override fun onBindViewHolder(originHolder: RecyclerView.ViewHolder, position: Int) {
        if(position == data.size) {
            val holder = originHolder as FooterHolder
            holder.bind()
        } else{
            val holder  = originHolder as Holder
            holder.bind(position)
            if(position != itemCount) {
                holder.name.text = data[position].name
                holder.content.text = data[position].contents
                if(data[position].type == 0) {
                    holder.type.setImageResource(R.drawable.img_tag_1)
                } else {
                    holder.type.setImageResource(R.drawable.img_tag_2)
                }
            }
        }

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val name : TextView = itemView.findViewById((R.id.text_home_help_cat_name))
        val content : TextView = itemView.findViewById(R.id.text_home_help_cat_content)
        val type : ImageView = itemView.findViewById(R.id.image_help_cat_tag)

        fun bind(position: Int) {
            itemView.setOnClickListener { itemClick(position) }
        }
    }

    inner class FooterHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.setOnClickListener { itemClick(data[position].idx) }
        }
    }


}
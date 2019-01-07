package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rescat.rescat_android.Get.GetMyPageFundResponse
import com.rescat.rescat_android.Get.GetMySupportingResponse
import com.rescat.rescat_android.R
import org.w3c.dom.Text
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MySupportRecyclcerViewAdapter(val ctx: Context, val mysupportinglist: ArrayList<GetMySupportingResponse>) : RecyclerView.Adapter<MySupportRecyclcerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySupportRecyclcerViewAdapter.Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_my_support, parent, false)
        return Holder(view)

    }



    override fun onBindViewHolder(holder: MySupportRecyclcerViewAdapter.Holder, position: Int) {
        holder.title.text = mysupportinglist[position].title
        holder.contents.text = mysupportinglist[position].contents
        holder.currentAmount.text = mysupportinglist[position].currentAmount.toString()
        holder.category.text = mysupportinglist[position].category.toString()



//        try {
//            var format = SimpleDateFormat("yyyy-MM-dd")
//
//            var time1 = format.parse(mysupportinglist[position].limitAt.split("T")[0])
//            var time2 = format.parse(Date())
//
//            var dDay = time1.time - time2.time
//
//            var dDate = dDay / (24 * 60 * 60 * 1000)
//
//            dDay = Math.abs(dDate)
//
//            Log.e("date split", "데이트 성공")
//
//
//            holder.dday.text = dDay.toString()
//        }catch (e : Exception)
//        {
//
//
//            Log.e("date split", "데이트 실패")
//            return e.printStackTrace()
//        }

        val requestOptions = RequestOptions()
        Glide.with(ctx)
            .setDefaultRequestOptions(requestOptions)
            .load(mysupportinglist[position].photos)
            .thumbnail(1.0f)
            .into(holder.photos)

        Log.e("viewholder","뷰홀더 완료")


    }


    override fun getItemCount(): Int = mysupportinglist.size



    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_my_support_list_title) as TextView
        val contents : TextView = itemView.findViewById(R.id.tv_my_support_content) as TextView
        val category : TextView = itemView.findViewById(R.id.tv_my_support_type) as TextView
       //  val dday : TextView = itemView.findViewById(R.id.tv_my_support_date) as TextView
        val currentAmount : TextView = itemView.findViewById(R.id.tv_my_support_current_amount) as TextView
        val photos : ImageView = itemView.findViewById(R.id.iv_my_support_photo) as ImageView


    }



}
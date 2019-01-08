package com.rescat.rescat_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.model.HouseType
import kotlinx.android.synthetic.main.item_radiobutton.view.*
import java.util.*


class RadiobuttonAdapter : ArrayAdapter<HouseType> {
    var houseList = ArrayList<HouseType>()
    var booleans = ArrayList<Boolean>()

    constructor(context: Context, resource :Int, houseList: ArrayList<HouseType>, mlist : ArrayList<Boolean>)
            : super(context, resource, houseList) {
        this.houseList = houseList
        this.booleans = mlist
    }

    override fun getCount(): Int {
        return houseList.size
    }

    override fun getItem(position: Int): HouseType {
        return houseList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val house = this.houseList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var radioView = inflator.inflate(R.layout.item_radiobutton, null)
        radioView.btn_radio.text = house.name
        radioView.btn_radio.setTag(Integer.valueOf(position))
        radioView.btn_radio.setChecked(booleans.get(position))
        radioView.btn_radio.setOnCheckedChangeListener { buttonView, isChecked ->
            Collections.replaceAll(booleans, true, false)
            booleans.set(buttonView.tag as Int, isChecked)
            var gridView = parent as GridView
            gridView.invalidateViews()
        }

        return radioView
    }

}
package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.rescat.rescat_android.Get.GetMySupportingResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.RegionData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MyLocationRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_my_location.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLocationActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    var mylocationlist  = ArrayList<RegionData>()

    lateinit var myLocationRecyclerViewAdapter : MyLocationRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_location)

        getMyLocationResponse()
        setRecyclerView()
        setOnBtnClickListener()


    }

    private fun setOnBtnClickListener() {

        btn_ac_modify_my_nickname_back.setOnClickListener {
            finish()
        }

    }


    private fun setRecyclerView() {
        myLocationRecyclerViewAdapter = MyLocationRecyclerViewAdapter(this,mylocationlist )
        rv_my_location_setting_list.adapter = myLocationRecyclerViewAdapter
        rv_my_location_setting_list.layoutManager = LinearLayoutManager(this)
    }

    private fun getMyLocationResponse() {
        Log.e("response", "리스폰스 들어옴")

        var getMyLocationResponse = networkService.getMyLocation()

        getMyLocationResponse.enqueue(object : Callback<ArrayList<RegionData>>{
            override fun onFailure(call: Call<ArrayList<RegionData>>, t: Throwable) {
                Log.e("my location error", "통신에러")
            }

            override fun onResponse(call: Call<ArrayList<RegionData>>, response: Response<ArrayList<RegionData>>) {
                if (response.isSuccessful){
                    Log.e("successful","내 지역 리스폰 성공")

                    var temp : ArrayList<RegionData> = response.body()!!
                    if (temp.size > 0) {
                        val position =   myLocationRecyclerViewAdapter.itemCount
                        myLocationRecyclerViewAdapter.mylocationlist.addAll(temp)
                        myLocationRecyclerViewAdapter.notifyItemInserted(position)
                    } else{
                        toast("실패")
                    }
                }
            }

        })


    }

}
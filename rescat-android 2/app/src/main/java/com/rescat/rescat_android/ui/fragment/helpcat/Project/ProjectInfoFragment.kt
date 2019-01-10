package com.rescat.rescat_android.ui.fragment.helpcat.Project

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.rescat.rescat_android.Get.GetFundingData
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.PhotoData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.helpcat.ProjectApplyActivity
import com.rescat.rescat_android.ui.adapter.SupportInfoBannerAdapter

import kotlinx.android.synthetic.main.fragment_support_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectInfoFragment :Fragment(){

    lateinit var infoBannerAdapter: SupportInfoBannerAdapter
    var idx: Int = 0

    var present : Boolean = true

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idx = arguments!!.getInt("idx")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        InitData()
        setButtonListener()
    }



    fun InitData() {
     val getFundingData : Call<GetFundingData> =
             networkService.getFundingData(idx,present)

        getFundingData.enqueue(object : Callback<GetFundingData>  {
            override fun onFailure(call: Call<GetFundingData>, t: Throwable) {
                Log.e("Get Data Fail", t.toString())
            }

            override fun onResponse(call: Call<GetFundingData>, response: Response<GetFundingData>) {
                if (response.isSuccessful){

                    //TODO. 데이터 셋팅
                    val data: GetFundingData = response.body()!!
                    setViewPager(data.photos)

                    text_support_info_title.text = data.title
                    text_support_info_introdution.text = data.introduction
                    tv_funding_current_amount.text = data.currentAmount.toString()
                    text_support_info_content.text = data.contents
                    tv_funding_limit.text=data.limitAt
                }
            }
        })
    }


    private fun setButtonListener() {
        btn_support_send.setOnClickListener {
            val intent: Intent = Intent(activity, ProjectApplyActivity::class.java)
            intent.putExtra("idx", idx)
            startActivity(intent)
        }
    }

    private fun setViewPager(photoList : ArrayList<PhotoData>) {
        infoBannerAdapter = SupportInfoBannerAdapter(photoList)
        vp_support_info_banner.adapter = infoBannerAdapter
    }
}
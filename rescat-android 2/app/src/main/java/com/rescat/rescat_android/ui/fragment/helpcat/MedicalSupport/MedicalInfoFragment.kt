package com.rescat.rescat_android.ui.fragment.helpcat.adopt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.Get.GetFundingData
import com.rescat.rescat_android.R

import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.PhotoData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MedicalInfoBannerAdapter

import kotlinx.android.synthetic.main.fragment_support_info.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicalInfoFragment : Fragment() {


    lateinit var infoBannerAdapter: MedicalInfoBannerAdapter
    var idx: Int = 0
    var present : Boolean = true

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idx = arguments!!.getInt("idx")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_info, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        InitData()
        setButtonListener()
    }

    private fun setButtonListener() {
    }


    fun InitData() {


        val getFundingData: Call<GetFundingData> =
            networkService.getFundingData(idx,present)

        getFundingData.enqueue(object: Callback<GetFundingData> {
            override fun onFailure(call: Call<GetFundingData>, t: Throwable) {
                Log.e("Get Data Fail", t.toString())
            }

            override fun onResponse(call: Call<GetFundingData>, response: Response<GetFundingData>) {
                if (response.isSuccessful){
                    //TODO. 데이터 셋팅
                    val data:GetFundingData = response.body()!!
                    setViewPager(data.photos)
                    text_support_info_introdution.text = data.introduction
                    text_support_info_title.text = data.title
                    text_support_info_content.text = data.contents
                    tv_funding_current_amount.text = data.currentAmount.toString()
                    funding_Dday.text = data.limitAt


                }else{
                    var message : String = "failed"
                    toast(message)
                }
            }
        })

    }

    private fun setViewPager(photoList : ArrayList<PhotoData>) {
        infoBannerAdapter = MedicalInfoBannerAdapter(photoList)
        vp_support_info_banner.adapter = infoBannerAdapter
    }

    companion object {

        fun newInstance(idx: Int): MedicalInfoFragment {
            val args = Bundle()
            args.putInt("idx", idx)
            val fragment = MedicalInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }



}

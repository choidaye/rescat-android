package com.rescat.rescat_android.ui.fragment.helpcat

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.Get.GetMainPageFunding
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.HelpCardData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import com.rescat.rescat_android.ui.activity.helpcat.SupportAddActivity
import com.rescat.rescat_android.ui.activity.helpcat.WriteActivity
import com.rescat.rescat_android.ui.adapter.HomeFundCardAdapter
import com.rescat.rescat_android.ui.adapter.HomeHelpCatAdapter
import com.rescat.rescat_android.ui.adapter.HomeMainBannerAdapter
import kotlinx.android.synthetic.main.fragment_help_cat.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelpCatFragment : Fragment() {


    lateinit var helpCatAdapter: HomeHelpCatAdapter
    lateinit var fundCardAdapter: HomeFundCardAdapter
    lateinit var mainBannerAdapter: HomeMainBannerAdapter

    val MAX_TIME: Long = Long.MAX_VALUE
    var timer: CountDownTimer? = null
    var data: ArrayList<Int> = ArrayList()
    var HelpCareData: ArrayList<HelpCardData> = ArrayList()
    var FundCardData: ArrayList<GetMainPageFunding> = ArrayList()

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
        setViewPager()
        setOnBtnClickListener()
        buttonInit()
    }

    private fun setOnBtnClickListener() {
        btn_support_writer.setOnClickListener {
            startActivity<SupportAddActivity>()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_cat, container, false)
    }

    override fun onPause() {
        super.onPause()

        timer?.cancel()
        timer = null
    }

    private fun buttonInit() {
        image_more_home_help_cat.setOnClickListener {
            (activity as MainActivity).addFragment(HelpFragment())
        }

        image_more_home_fund_card.setOnClickListener {
            (activity as MainActivity).addFragment(FundFragment())
        }

        btn_support_writer.setOnClickListener {
            startActivity<WriteActivity>()
        }
    }

    private fun setRecyclerView() {
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)

        val getHelpPostMain: Call<ArrayList<HelpCardData>> =
            networkService.getHelpPostMain()

        getHelpPostMain.enqueue(object: Callback<ArrayList<HelpCardData>> {
            override fun onFailure(call: Call<ArrayList<HelpCardData>>, t: Throwable) {
                Log.e("Main page adopt", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<HelpCardData>>, response: Response<ArrayList<HelpCardData>>) {
                if (response.isSuccessful){
                    HelpCareData = response.body()!!
                    helpCatAdapter = HomeHelpCatAdapter(HelpCareData)
                    rv_home_help_cat.adapter = helpCatAdapter
                    rv_home_help_cat.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

                }else{
                    var message : String = "failed"
                    toast(message)
                }
            }
        })



        val getMainPageFunding : Call<ArrayList<GetMainPageFunding>>  =
                networkService.getMainFunding()

        getMainPageFunding.enqueue(object : Callback<ArrayList<GetMainPageFunding>>{
            override fun onFailure(call: Call<ArrayList<GetMainPageFunding>>, t: Throwable) {
                Log.e("Main page Fund", t.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetMainPageFunding>>, response: Response<ArrayList<GetMainPageFunding>>) {

                if (response.isSuccessful){
                    FundCardData = response.body()!!
                    fundCardAdapter = HomeFundCardAdapter(FundCardData)
                    rv_home_fund_card.adapter = fundCardAdapter
                    rv_home_fund_card.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

                }

            }
        })

    }

    private fun setViewPager() {
        data.add(1)
        data.add(1)

        mainBannerAdapter = HomeMainBannerAdapter(data)
        vp_home_main_banner.adapter = mainBannerAdapter

        timer = object : CountDownTimer(MAX_TIME, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                if (vp_home_main_banner.currentItem == data.size-1) {
                    vp_home_main_banner.currentItem = 0
                } else {
                    vp_home_main_banner.currentItem = vp_home_main_banner.currentItem + 1
                }
            }

            override fun onFinish() {

            }
        }?.start()

//        Timer("ChangeHomeMainBanner").schedule(3000) {
//            if (vp_home_main_banner.currentItem == data.size-1) {
//                vp_home_main_banner.currentItem = 0
//            } else {
//                vp_home_main_banner.currentItem = vp_home_main_banner.currentItem + 1
//            }
//        }
    }
}

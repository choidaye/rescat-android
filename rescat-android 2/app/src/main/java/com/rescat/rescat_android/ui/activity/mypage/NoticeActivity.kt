package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.rescat.rescat_android.Get.GetMyNoticeResponse
import com.rescat.rescat_android.Get.GetMySupportingResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MyNoticeRecyclerViewAdapter
import com.rescat.rescat_android.ui.adapter.MySupportRecyclcerViewAdapter
import kotlinx.android.synthetic.main.activity_my_support.*
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    lateinit var noticeRecyclerViewAdapter: MyNoticeRecyclerViewAdapter


    var noticelist  = ArrayList<GetMyNoticeResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        getMyNoticeResponse()
        setRecyclerView()
    }




    private fun setRecyclerView() {

        noticeRecyclerViewAdapter = MyNoticeRecyclerViewAdapter(this, noticelist)
        rv_ac_notice_list.adapter = noticeRecyclerViewAdapter
        rv_ac_notice_list.layoutManager = LinearLayoutManager(this)
    }


    private fun getMyNoticeResponse() {
        Log.e("response", "리스폰스 들어옴")

        var  getMyNoticeResponse  = networkService.getMyNoticeList()
            getMyNoticeResponse.enqueue(object  : Callback<ArrayList<GetMyNoticeResponse>> {
            override fun onFailure(call: Call<ArrayList<GetMyNoticeResponse>>, t: Throwable) {
                Log.e("my support error", "통신에러")
            }

            override fun onResponse(call: Call<ArrayList<GetMyNoticeResponse>>, response: Response<ArrayList<GetMyNoticeResponse>>) {

                if (response.isSuccessful){
                    Log.e("successful","내 후원 리스폰 성공")
                    var temp : ArrayList<GetMyNoticeResponse> = response.body()!!
                    if (temp.size > 0) {
                        val position =  noticeRecyclerViewAdapter.itemCount
                        noticeRecyclerViewAdapter.noticelist.addAll(temp)
                        noticeRecyclerViewAdapter.notifyItemInserted(position)
                    } else{
                        toast("실패")
                    }
                }
            }

        })


    }

}

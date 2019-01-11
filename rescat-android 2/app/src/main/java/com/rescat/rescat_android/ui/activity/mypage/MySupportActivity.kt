package com.rescat.rescat_android.ui.activity.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.rescat.rescat_android.Get.GetMySupportingResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MySupportRecyclcerViewAdapter
import kotlinx.android.synthetic.main.activity_my_support.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySupportActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    lateinit var mysuuportRecyclerViewAdapter: MySupportRecyclcerViewAdapter

    var mysupportingList  = ArrayList<GetMySupportingResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_support)

        setRecyclerView()
        getMySupportResponse()

    }

    private fun setRecyclerView() {

        mysuuportRecyclerViewAdapter = MySupportRecyclcerViewAdapter(this,mysupportingList)
        rv_my_support_list.adapter = mysuuportRecyclerViewAdapter
        rv_my_support_list.layoutManager = LinearLayoutManager(this)
    }


    private fun getMySupportResponse() {
        Log.e("response", "리스폰스 들어옴")

        var getMySupportingResponse = networkService.getMySupprting()

        getMySupportingResponse.enqueue(object  : Callback<ArrayList<GetMySupportingResponse>> {
            override fun onFailure(call: Call<ArrayList<GetMySupportingResponse>>, t: Throwable) {
                Log.e("my support error", "통신에러")
            }

            override fun onResponse(call: Call<ArrayList<GetMySupportingResponse>>, response: Response<ArrayList<GetMySupportingResponse>>) {

                if (response.isSuccessful){
                    Log.e("successful","내 후원 리스폰 성공")
                    var temp : ArrayList<GetMySupportingResponse> = response.body()!!
                    if (temp.size > 0) {
                        val position =  mysuuportRecyclerViewAdapter.itemCount
                        mysuuportRecyclerViewAdapter.mysupportinglist.addAll(temp)
                        mysuuportRecyclerViewAdapter.notifyItemInserted(position)
                    } else{
                        toast("실패")
                    }
                }
            }

        })


    }

}


package com.rescat.rescat_android.ui.fragment.mypage.mypost

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.Get.GetMyPageCareResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import com.rescat.rescat_android.ui.adapter.MyPostCareRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_my_post_care.*
import kotlinx.android.synthetic.main.rv_item_my_post_care.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostCareFragment : Fragment() {

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    lateinit var mycareRecyclerViewAdapter: MyPostCareRecyclerViewAdapter

    var mycareList  = ArrayList<GetMyPageCareResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_post_care, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setRecyclerView()
        getMyPostCareResponse()
        setOnCLickListener()
    }



    private fun setOnCLickListener() {
//        rv_my_post_cafe_list_item.setOnClickListener {
//
//        }
    }


    private fun setRecyclerView() {
        mycareRecyclerViewAdapter = MyPostCareRecyclerViewAdapter(activity!!,mycareList)
        rv_my_post_care_list.adapter = mycareRecyclerViewAdapter
        rv_my_post_care_list.layoutManager = LinearLayoutManager(activity!!)
    }

    private fun getMyPostCareResponse() {

        Log.e("response", "리스폰스 들어옴")

        val getMyPageCareResponse = networkService.getMyPostCare()

        getMyPageCareResponse.enqueue(object : Callback<ArrayList<GetMyPageCareResponse>> {
            override fun onFailure(call: Call<ArrayList<GetMyPageCareResponse>>, t: Throwable) {
                Log.e("careresponse", "통신에러")
            }

            override fun onResponse(call: Call<ArrayList<GetMyPageCareResponse>>, response: Response<ArrayList<GetMyPageCareResponse>>) {
                if (response.isSuccessful) {

                    Log.e("successful","마이페이지 케어 리스폰 성공")


                    var temp : ArrayList<GetMyPageCareResponse> = response.body()!!
                    if (temp.size > 0) {
                        val position = mycareRecyclerViewAdapter.itemCount
                        mycareRecyclerViewAdapter.mypostcarelist.addAll(temp)
                        mycareRecyclerViewAdapter.notifyItemInserted(position)

                    } else{
                       toast("실패")
                    }
                }
            }

        })


    }

}

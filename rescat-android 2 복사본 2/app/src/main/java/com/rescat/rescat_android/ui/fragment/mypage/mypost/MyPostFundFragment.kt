package com.rescat.rescat_android.ui.fragment.mypage.mypost

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rescat.rescat_android.Get.GetMyPageCareResponse
import com.rescat.rescat_android.Get.GetMyPageFundResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.MyPostFundRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_my_post_fund.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostFundFragment : Fragment(){


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    lateinit var myfundRecyclerViewAdapter: MyPostFundRecyclerViewAdapter

    var mypostfundList = ArrayList<GetMyPageFundResponse>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_post_fund, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setRecyclerView()
        getMyPostFundResponse()

    }



    private fun setRecyclerView() {
        myfundRecyclerViewAdapter = MyPostFundRecyclerViewAdapter(activity!!,mypostfundList)
        rv_my_post_fund_list.adapter =myfundRecyclerViewAdapter
        rv_my_post_fund_list.layoutManager = LinearLayoutManager(activity!!)
    }


    private fun getMyPostFundResponse() {
        Log.e("response", "리스폰스 들어옴")

        val getMyPageFundResponse = networkService.getMyPostFund()

        getMyPageFundResponse.enqueue(object : Callback< ArrayList<GetMyPageFundResponse>> {
            override fun onFailure(call: Call<ArrayList<GetMyPageFundResponse>>, t: Throwable) {
                Log.e("fundresponse", "통신에러")
            }

            override fun onResponse(
                call: Call<ArrayList<GetMyPageFundResponse>>,response: Response<ArrayList<GetMyPageFundResponse>>) {

                if (response.isSuccessful){
                    Log.e("successful","마이페이지 케어 리스폰 성공")

                    var temp : ArrayList<GetMyPageFundResponse> = response.body()!!
                    if (temp.size > 0){
                        val position = myfundRecyclerViewAdapter.itemCount
                        myfundRecyclerViewAdapter.mypostfundList.addAll(temp)
                        myfundRecyclerViewAdapter.notifyItemInserted(position)

                    }

                    else{
                        toast("실패")
                    }
                }

            }

        })
    }
}

package com.rescat.rescat_android.ui.fragment.helpcat.adopt

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.HelpPostData
import com.rescat.rescat_android.model.PhotoData
import com.rescat.rescat_android.model.Vaccination
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.helpcat.AdoptApplyActivity
import com.rescat.rescat_android.ui.adapter.AdoptInfoBannerAdapter
import kotlinx.android.synthetic.main.fragment_adopt_info.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptInfoFragment : Fragment() {

    lateinit var infoBannerAdapter: AdoptInfoBannerAdapter
    var idx: Int = 0
    var isFinished: Boolean = false

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
        image_adopt_info.imageResource = R.drawable.img_adopt
        btn_adopt_send.text = "입양할래요"
        val getHelpPostData: Call<HelpPostData> =
            networkService.getHelpDetailData(idx)

        getHelpPostData.enqueue(object: Callback<HelpPostData> {
            override fun onFailure(call: Call<HelpPostData>, t: Throwable) {
                Log.e("Get Data Fail", t.toString())
            }

            override fun onResponse(call: Call<HelpPostData>, response: Response<HelpPostData>) {
                if (response.isSuccessful){
                    //TODO. 데이터 셋팅
                    val data:HelpPostData = response.body()!!
                    setViewPager(data.photos)
                    isFinished = data.isFinished
                    text_adopt_info_content.text = data.contents
                    text_adopt_info_name.text = data.name
                    text_adopt_info_age.text = data.age
                    text_adopt_info_uniqueness.text = data.etc
                    text_adopt_info_vaccination.text = Vaccination.valueOf(data.vaccination).name
                    if(data.tnr == 0) {
                        text_adopt_info_tnr.text = "안함"
                    } else {
                        text_adopt_info_tnr.text = "함"
                    }

                    text_adopt_info_kind.text = data.breed
                    if(data.sex == 0) {
                        text_adopt_info_gender.text = "남자"
                    } else {
                        text_adopt_info_gender.text = "여자"
                    }


                }else{
                    var message : String = "failed"
                    toast(message)
                }
            }
        })

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adopt_info, container, false)
    }

    private fun setViewPager(photoList : ArrayList<PhotoData>) {

        infoBannerAdapter = AdoptInfoBannerAdapter(photoList)
        vp_adopt_info_banner.adapter = infoBannerAdapter
    }

    private fun setButtonListener() {
        btn_adopt_send.setOnClickListener {
            if(isFinished) {
                Toast.makeText(activity!!, "신청이 완료된 글입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent: Intent = Intent(activity, AdoptApplyActivity::class.java)
                intent.putExtra("idx", idx)
                startActivity(intent)
            }
        }
    }

    companion object {

        fun newInstance(idx: Int): AdoptInfoFragment {
            val args = Bundle()
            args.putInt("idx", idx)
            val fragment = AdoptInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

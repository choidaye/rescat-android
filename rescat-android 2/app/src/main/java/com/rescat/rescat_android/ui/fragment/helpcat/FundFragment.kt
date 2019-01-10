package com.rescat.rescat_android.ui.fragment.helpcat

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rescat.rescat_android.Get.GetFundingResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.R.id.*
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import com.rescat.rescat_android.ui.activity.helpcat.MedicalActivity
import com.rescat.rescat_android.ui.activity.helpcat.ProjectActivity
import com.rescat.rescat_android.ui.adapter.SupportAdapter
import kotlinx.android.synthetic.main.fragment_fund.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FundFragment : Fragment() {


    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    lateinit var supportMedicalListAdapter: SupportAdapter
    lateinit var supportProjectListAdapter : SupportAdapter


    var data: ArrayList<GetFundingResponse> = ArrayList()


    var mapping: Map<TextView, RecyclerView> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()

        buttonInit()
        setupUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fund, container, false)
    }


    private fun setupUI() {
        changeRadioButton(text_medical_fee)
    }

    private fun buttonInit() {

        mapping = mapOf(Pair(text_medical_fee, rv_support_medical), Pair(text_project_support, rv_support_project))
        text_medical_fee.setOnClickListener {
            changeRadioButton(it as TextView)
        }

        text_project_support.setOnClickListener {
            changeRadioButton(it as TextView)
        }

        btn_fund_back.setOnClickListener {
            (activity as MainActivity).addFragment(HelpCatFragment())
        }

    }

    private fun setRecyclerView() {


       //Get Medical data

        val getMedicalResponse : Call <ArrayList<GetFundingResponse>> =
                networkService.getFundingMainList(0)

        getMedicalResponse.enqueue(object : Callback<ArrayList<GetFundingResponse>>{
            override fun onFailure(call: Call<ArrayList<GetFundingResponse>>, t: Throwable) {
                Log.e("medical fail", t.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetFundingResponse>>, response: Response<ArrayList<GetFundingResponse>>) {

                if (response.isSuccessful){

                    supportMedicalListAdapter = SupportAdapter(response.body()!!){

                        val intent : Intent = Intent(activity, MedicalActivity::class.java)
                        intent.putExtra("idx",it)
                        startActivity(intent)
                    }

                    rv_support_medical.adapter = supportMedicalListAdapter
                    rv_support_medical.layoutManager = LinearLayoutManager(activity)
                }
            }
        })



        //Get Project data

        val getProjectResponse: Call<ArrayList<GetFundingResponse>> =
        networkService.getFundingMainList(1)

        getProjectResponse.enqueue(object : Callback<ArrayList<GetFundingResponse>>{
            override fun onFailure(call: Call<ArrayList<GetFundingResponse>>, t: Throwable) {
                Log.e("project Fail", t.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetFundingResponse>>, response: Response<ArrayList<GetFundingResponse>>) {

                if (response.isSuccessful){

                    supportProjectListAdapter = SupportAdapter(response.body()!!){

                        val intent : Intent = Intent(activity, ProjectActivity::class.java)
                        intent.putExtra("idx", it)
                        startActivity(intent)
                    }

                    rv_support_project.adapter = supportProjectListAdapter
                    rv_support_project.layoutManager = LinearLayoutManager(activity)
                }
            }
        })

        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_support_project.addItemDecoration(itemDecoration)
            }
        }
    }

    private fun changeRadioButton(textview: TextView) {
        selectedTextButton(textview)

        when (textview) {
            text_medical_fee-> {
                unselectedTextButton(text_project_support)
            }
            text_project_support -> {
                unselectedTextButton(text_medical_fee)
            }
        }
    }

    private fun selectedTextButton(textview: TextView) {
        textview.isSelected = true
        textview.setTypeface(textview.typeface, Typeface.BOLD)
        mapping!!.get(textview)!!.visibility = View.VISIBLE
    }

    private fun unselectedTextButton(textview: TextView) {
        textview.isSelected = false
        textview.setTypeface(textview.typeface, Typeface.NORMAL)
        mapping!!.get(textview)!!.visibility = View.GONE
    }
}

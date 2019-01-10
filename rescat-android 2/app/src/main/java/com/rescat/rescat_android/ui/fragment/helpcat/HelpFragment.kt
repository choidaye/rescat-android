package com.rescat.rescat_android.ui.fragment.helpcat

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.HelpCardData
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import com.rescat.rescat_android.ui.activity.helpcat.ProtectActivity
import com.rescat.rescat_android.ui.adapter.HelpCatAdapter
import kotlinx.android.synthetic.main.fragment_help.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelpFragment : Fragment() {

    lateinit var helpCatAdapter: HelpCatAdapter
    lateinit var helpCatProtectAdapter: HelpCatAdapter

    var data: ArrayList<HelpCardData> = ArrayList()

    var mapping: Map<TextView, RecyclerView> ?= null

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

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
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    private fun setupUI() {
        changeRadioButton(text_fund_fee)
    }

    private fun buttonInit() {
        mapping = mapOf(Pair(text_fund_fee, rv_help_cat_adopt), Pair(text_help_protection, rv_help_cat_protect))
        text_fund_fee.setOnClickListener {
            changeRadioButton(it as TextView)
        }

        text_help_protection.setOnClickListener {
            changeRadioButton(it as TextView)
        }

        btn_help_back.setOnClickListener {
            (activity as MainActivity).addFragment(HelpCatFragment())
        }
    }

    private fun setRecyclerView() {
        //Get Help Care Data
        val getHelpCatData: Call<ArrayList<HelpCardData>> =
            networkService.getHelpCareCard(0)

        getHelpCatData.enqueue(object :Callback<ArrayList<HelpCardData>> {
            override fun onFailure(call: Call<ArrayList<HelpCardData>>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<HelpCardData>>, response: Response<ArrayList<HelpCardData>>) {
                if (response.isSuccessful){
                    helpCatAdapter = HelpCatAdapter(response.body()!!) {
                        val intent: Intent = Intent(activity, AdoptActivity::class.java)
                        intent.putExtra("idx", it)
                        startActivity(intent)
                    }
                    rv_help_cat_adopt.adapter = helpCatAdapter
                    rv_help_cat_adopt.layoutManager = LinearLayoutManager(activity)

                }else {
                    var message : String = "failed"
                    toast(message)
                }
            }
        })

        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_help_cat_adopt.addItemDecoration(itemDecoration)
            }
        }


        //Get Adopt Data
        val getAdoptCatData: Call<ArrayList<HelpCardData>> =
            networkService.getHelpCareCard(1)

        getAdoptCatData.enqueue(object :Callback<ArrayList<HelpCardData>> {
            override fun onFailure(call: Call<ArrayList<HelpCardData>>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<ArrayList<HelpCardData>>, response: Response<ArrayList<HelpCardData>>) {
                if (response.isSuccessful){
                    helpCatProtectAdapter = HelpCatAdapter(response.body()!!) {
                        val intent: Intent = Intent(activity, ProtectActivity::class.java)
                        intent.putExtra("idx", it)
                        startActivity(intent)
                    }
                    rv_help_cat_protect.adapter = helpCatProtectAdapter
                    rv_help_cat_protect.layoutManager = LinearLayoutManager(activity)

                }else {
                    var message : String = "failed"
                    toast(message)
                }
            }
        })

        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_help_cat_protect.addItemDecoration(itemDecoration)
            }
        }
    }

    private fun changeRadioButton(textview: TextView) {
        selectedTextButton(textview)

        when (textview) {
            text_fund_fee -> {
                unselectedTextButton(text_help_protection)
            }
            text_help_protection -> {
                unselectedTextButton(text_fund_fee)
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

    private fun setWriteButtonListner() {
        btn_write_help.setOnClickListener {
            //TODO.작성페이지로 넘어가는거!
        }
    }

}
package com.rescat.rescat_android.ui.fragment.helpcat

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.rescat.rescat_android.R
import com.rescat.rescat_android.ui.activity.helpcat.AdoptActivity
import com.rescat.rescat_android.ui.adapter.HelpCatAdapter
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment : Fragment() {

    lateinit var helpCatAdapter: HelpCatAdapter

    var data: ArrayList<Int> = ArrayList()

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
        text_fund_fee.setOnClickListener {
            changeRadioButton(it as TextView)
        }

        text_help_protection.setOnClickListener {
            changeRadioButton(it as TextView)
        }
    }

    private fun setRecyclerView() {
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)
        data.add(1)

        helpCatAdapter = HelpCatAdapter(data) {
            val intent: Intent = Intent(activity, AdoptActivity::class.java)
            startActivity(intent)
        }
        rv_help_cat.adapter = helpCatAdapter
        rv_help_cat.layoutManager = LinearLayoutManager(activity)

//        rv_help_cat.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        context?.let {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

            ContextCompat.getDrawable(it, R.drawable.line_divider)?.let {
                itemDecoration.setDrawable(it)
                rv_help_cat.addItemDecoration(itemDecoration)
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
    }

    private fun unselectedTextButton(textview: TextView) {
        textview.isSelected = false
        textview.setTypeface(textview.typeface, Typeface.NORMAL)
    }

}
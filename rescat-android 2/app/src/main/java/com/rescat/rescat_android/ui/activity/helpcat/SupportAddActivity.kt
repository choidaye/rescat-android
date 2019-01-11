package com.rescat.rescat_android.ui.activity.helpcat

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.rescat.rescat_android.Post.PostFundAdd
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_support_add.*
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class SupportAddActivity : AppCompatActivity() {


    var title : String = ""
    var introduction : String = ""
    var contents : String = ""
    var goalAmount : String = ""
    var limitAt : String = ""
    var mainRegion : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_add)

        setDatePicker()
        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {
        btn_support_next.setOnClickListener {

            if (et_ac_add_support_title.text.toString().isNotEmpty()&&et_support_add_simple_info.text.toString().isNotEmpty()&&et_support_add_content.text.toString().isNotEmpty()
                &&tv_support_add_targe_amount.text.toString().isNotEmpty()&&et_support_add_limit_date.text.toString().isNotEmpty()){

                var intent = Intent(applicationContext, SupportAddUserActivity::class.java)

                intent.putExtra("title",title)
                intent.putExtra("introduction", introduction)
                intent.putExtra("contents",contents)
                intent.putExtra("limitAt",limitAt)
                intent.putExtra("goalAmount",goalAmount).toString()
                intent.putExtra("mainRegion",mainRegion)





            }
        }

    }


    private fun setDatePicker() {
        val textView: TextView = findViewById(R.id.et_support_add_limit_date)
        textView.text= SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)

        }

        textView.setOnClickListener {
            DatePickerDialog(this@SupportAddActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }


}

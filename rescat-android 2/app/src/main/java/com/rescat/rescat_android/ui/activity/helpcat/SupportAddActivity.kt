package com.rescat.rescat_android.ui.activity.helpcat

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_support_add.*
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class SupportAddActivity : AppCompatActivity() {



    var title : String = et_ac_add_support_title.text.toString()
    var introduction: String = et_support_add_simple_info.text.toString()
    var contents : String = et_support_add_content.text.toString()
    var mainRegion : String = et_support_add_location.text.toString()
    var limitAt : String = et_support_add_limit_date.text.toString()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_add)


        setDatePicker()
        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        btn_support_next.setOnClickListener {

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

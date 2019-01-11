package com.rescat.rescat_android.ui.activity.helpcat

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.rescat.rescat_android.Post.PostCareApplication
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.HouseType
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.adapter.RadiobuttonAdapter
import com.rescat.rescat_android.utils.ErrorBodyConverter
import kotlinx.android.synthetic.main.activity_adopt_apply.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdoptApplyActivity : Activity() {
    var idx: Int = 0
    var TEST_TOKEN:String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6MSwiZXhwIjoxNTQ4NzU2OTEwfQ.XC0S5ywa4DYU4JYxenSio-4q7Pn-SDVyv0MY4S-_IeM"
    var COMPANIONTYPE = mutableMapOf(R.id.btn_radio_companion to true,
        R.id.btn_radio_no_companion to false)
    var birth :Date  = Date()
    lateinit var radioAdapter : RadiobuttonAdapter

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_apply)
        val intent = intent
        idx = intent.getIntExtra("idx", 0)
        setInitLayout()
        setbtnListener()

    }

    private fun setInitLayout() {
        text_help_apply_title.text = "입양신청"
        text_help_apply_header_desc.text = "작성한 개인정보는 입양을 위해 글 게시자에게 보여집니다."
        text_adopt_address_info.text = "입양글 작성자가 참고할 수 있도록 \n고양이를 반려할 주소를 대략적으로 적어주세요."
        edit_adopt_apply_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        var HouseList : ArrayList<HouseType> = ArrayList()
        HouseList.add(HouseType(0, "아파트", "APARTMENT"))
        HouseList.add(HouseType(1, "주택", "HOUSING"))
        HouseList.add(HouseType(2, "다세대주택", "MULTIFLEX"))
        HouseList.add(HouseType(3, "원룸", "ONEROOM"))
        var booleans :ArrayList<Boolean> = ArrayList(4)
        booleans.addAll(listOf(true, false, false, false))
        radioAdapter = RadiobuttonAdapter(applicationContext, android.R.layout.simple_list_item_checked, HouseList, booleans)
        gridView.adapter = radioAdapter

        setDatePicker()
    }

    private fun setbtnListener(){
        btn_adopt_complete.setOnClickListener {
            show()
        }

        btn_adopt_apply_back.setOnClickListener{
            finish()
        }

    }

    private fun setDatePicker() {
        val textView: TextView  = findViewById(R.id.edit_adopt_apply_birth)
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
            DatePickerDialog(this@AdoptApplyActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun show() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("정말 입양 신청하시겠습니까?")
        builder.setMessage("한 생명을 끝까지 책임진다는 \n신중한 마음으로 결정을 내려주세요.")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("확인") { dialog, which ->
            sendData()
        }

        builder.setNegativeButton("취소") { dialog, which ->

        }
        val dialog = builder.create()

        dialog.show()

        // Get the alert dialog buttons reference
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//
//        val titleTextView = dialog.findViewById(android.R.id.title) as TextView
//        titleTextView.textSize = 18f
//        titleTextView.setLineSpacing(28f, 1.0f)

        val textView = dialog.findViewById(android.R.id.message) as TextView
        textView.textSize = 14f
        textView.setLineSpacing(24f,1.0f)
        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor("#FF6e4d37"))

        negativeButton.setTextColor(Color.parseColor("#FFf29191"))


    }

    private fun sendData() {
        val name = edit_adopt_apply_name.text.toString()
        val phone = edit_adopt_apply_phone.text.toString()
        val birth = edit_adopt_apply_birth.text.toString()
        val job = edit_adopt_apply_job.text.toString()
        val houseType = (radioAdapter.getItem(radioAdapter.booleans.indexOf(true))).type
        val companionExperience = COMPANIONTYPE.get(radio_group_companion.checkedRadioButtonId)
        val finalWord = edit_adopt_apply_comment.text.toString()
        val address = edit_adopt_apply_address2.text.toString()

        val applicationData : PostCareApplication = PostCareApplication(0, name, phone, birth, address, job, houseType!!, companionExperience!!, finalWord)

        val postCareApplyApplication: Call<ResponseBody> = networkService.postCareApplication( applicationData, idx)
        postCareApplyApplication.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Adopt Apply Fail", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    Log.i("test", "success")
                    val intent: Intent = Intent(this@AdoptApplyActivity, AdoptCompleteActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    var errorMessage :String = ""
                    if(response.code() == 400) {
                        errorMessage = ErrorBodyConverter.convert400(response.errorBody()!!)

                    } else {
                        errorMessage = ErrorBodyConverter.convert(response.errorBody()!!)
                    }

                    Toast.makeText(this@AdoptApplyActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}

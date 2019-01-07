package com.rescat.rescat_android.ui.activity.helpcat

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import com.google.gson.annotations.SerializedName
import com.rescat.rescat_android.Post.PostCareApplication
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import kotlinx.android.synthetic.main.activity_adopt_apply.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class AdoptApplyActivity : Activity() {
    var idx: Int = 0
    var TEST_TOKEN:String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6MSwiZXhwIjoxNTQ4NzU2OTEwfQ.XC0S5ywa4DYU4JYxenSio-4q7Pn-SDVyv0MY4S-_IeM"
    var HOUSETYPE = mutableMapOf(R.id.btn_radio_apartment to "APARTMENT",
                                 R.id.btn_radio_house to "HOUSING",
                                 R.id.btn_radio_multiflex to "MULTIFLEX",
                                 R.id.btn_radio_oneroom to "ONEROOM")
    var COMPANIONTYPE = mutableMapOf(R.id.btn_radio_companion to true,
                                     R.id.btn_radio_no_companion to false)
    var checkId :Int = R.id.btn_radio_apartment
    var birth :Date  = Date()

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
        var isChange :Boolean = false
        text_help_apply_title.text = "입양신청"
        text_help_apply_header_desc.text = "입양을 하시나요?\n당신의 아름다운 결심을 지지합니다."
        edit_adopt_apply_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        radio_group_house_type?.setOnCheckedChangeListener { group, checkedId ->
            if(radio_group_house_type2.checkedRadioButtonId != -1) {
                if (isChange) {
                    isChange = false
                } else {
                    isChange = true
                    val checked = findViewById<RadioButton>(radio_group_house_type2.checkedRadioButtonId)
                    checked.isChecked = false
                    checkId = checkedId
                }
            }
        }

        radio_group_house_type2?.setOnCheckedChangeListener { group, checkedId ->
            if(radio_group_house_type.checkedRadioButtonId != -1) {
                if(isChange) {
                    isChange = false
                } else {
                    isChange = true
                    val checked = findViewById<RadioButton>(radio_group_house_type.checkedRadioButtonId)
                    checked.isChecked = false
                    checkId = checkedId
                }
            }
        }

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
        val houseType = HOUSETYPE.get(checkId)
        val companionExperience = COMPANIONTYPE.get(radio_group_companion.checkedRadioButtonId)
        val finalWord = edit_adopt_apply_comment.text.toString()
        val address = edit_adopt_apply_address2.text.toString()

        val applicationData : PostCareApplication = PostCareApplication(0, name, phone, birth, address, job, houseType!!, companionExperience!!, finalWord)

        val postCareApplyApplication: Call<ResponseBody> = networkService.postCareApplication(TEST_TOKEN, applicationData, idx)

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
                    //TODO. get Error Message
//                    val restError = RescatApplication.instance.retrofit.responseBodyConverter<RestError>(
//                        RestError::class.java,
//                        RestError::class.java.annotations
//                    ).convert(response.errorBody()) as RestError
//                    val errorMessage = restError.text.message
                      val errorMessage = "TEST"

                    Log.e("Error Response","Error" + errorMessage)
                }
            }
        })

    }

    class RestError {
         @SerializedName("text")
         var text :ErrorData = ErrorData()
     }

    class ErrorData {
        @SerializedName("field")
        var field :String =""
        @SerializedName("message")
        var message :String =""
    }

}


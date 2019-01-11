package com.rescat.rescat_android.utils

import java.text.ParseException
import java.text.SimpleDateFormat

class ConvertData {
    companion object {

        private object TIME_MAXIMUM {
            val SEC = 60
            val MIN = 60 * SEC
        }

        fun convertDateToString(createAt: String?) : String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            try {
                val create = format.parse(createAt)
                val createTime = (create).time
                val curTime = System.currentTimeMillis()
                var diffTime = (curTime - createTime) / 1000
                var msg: String
                if (diffTime < TIME_MAXIMUM.SEC) {
                    msg = "방금 전"
                } else if ((diffTime/TIME_MAXIMUM.SEC.toLong()) < 60) {
                    msg = (diffTime/TIME_MAXIMUM.SEC.toLong()).toString() + "분 전"
                } else if ((diffTime/(TIME_MAXIMUM.MIN.toLong())) < 24) {
                    msg = (diffTime/TIME_MAXIMUM.MIN.toLong()).toString() + "시간 전"
                } else {
                    msg = (create.month+1).toString() +"월 " + create.day.toString()+"일"
                }
                return msg
            } catch (e: ParseException) {
                return " "
            }
        }
    }

}
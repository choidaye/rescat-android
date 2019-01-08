package com.rescat.rescat_android.utils

import com.google.gson.internal.LinkedTreeMap
import com.rescat.rescat_android.application.RescatApplication
import okhttp3.ResponseBody

class ErrorBodyConverter {
    companion object {
        fun convert(errorbody : ResponseBody) :String {
            val restError = RescatApplication.instance.retrofit.responseBodyConverter<ArrayList<LinkedTreeMap<String, String>>>(
                ArrayList::class.java,
                ArrayList::class.java.annotations
            ).convert(errorbody) as ArrayList<LinkedTreeMap<String, String>>
            return restError.get(0).get("message") as String
        }
    }
}

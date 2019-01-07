package com.rescat.rescat_android.network


import android.util.Log
import com.rescat.rescat_android.application.RescatApplication
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.Response



//헤더에 권한 주는거 콜 불러주는 아이

class RescatNetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        (request to builder).apply {
            val token = RescatApplication.preference.token
            if (token != "") {
                addHeader("Authorization", token)
            }
            if (request.method().toUpperCase() != "GET" && request.body() !is MultipartBody) {
                addHeader("Content-Type", "application/json; charset=utf-8")
            }
        }
        return chain.proceed(builder.build())
    }

    private fun Pair<Request, Request.Builder>.addHeader(key: String, value: String?) {
        val request = first
        val builder = second
        if (first.header(key) != null) {
            return
        }
        if (value == null) {
            Log.wtf(TAG, "value is null. key: $key, request: $request")
            return
        }
        builder.addHeader(key, value)
    }

    companion object {
        private const val TAG = "RNetworkInterceptor"
    }
}
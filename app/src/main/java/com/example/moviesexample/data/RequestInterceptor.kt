package com.example.moviesexample.data

import com.example.androidengineerassignmentsaulov2.utils.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url()
        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val request: Request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
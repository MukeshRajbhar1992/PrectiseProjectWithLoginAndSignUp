package com.example.practiseproject.retrofit

import com.example.practiseproject.BuildConfig.SWAGGER_BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val retrofitJsonInstance: Retrofit.Builder by lazy {
        val httpIntercept = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(httpIntercept)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }.build()
        Retrofit.Builder()
            .baseUrl(SWAGGER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }
    val apiInterface: ApiService by lazy {
        retrofitJsonInstance.build().create(ApiService::class.java)
    }
}
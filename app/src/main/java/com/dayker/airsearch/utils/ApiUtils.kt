package com.dayker.airsearch.utils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {

    fun retrofitInit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

}
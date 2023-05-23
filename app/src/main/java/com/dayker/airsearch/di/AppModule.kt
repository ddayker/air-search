package com.dayker.airsearch.di

import android.content.Context
import android.content.SharedPreferences
import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.ApiUtils.retrofitInit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { retrofitInit().create(ApiService::class.java) }

}
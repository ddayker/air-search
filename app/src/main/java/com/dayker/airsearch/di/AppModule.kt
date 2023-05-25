package com.dayker.airsearch.di

import com.dayker.airsearch.database.ProjectDatabase
import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.ApiUtils.retrofitInit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { retrofitInit().create(ApiService::class.java) }
    single {
        ProjectDatabase.getAppDatabaseInstance(androidContext().applicationContext)
    }
    single { get<ProjectDatabase>().flightDao() }
}
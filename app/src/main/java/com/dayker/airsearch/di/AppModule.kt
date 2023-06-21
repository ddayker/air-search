package com.dayker.airsearch.di

import android.content.Context
import android.content.SharedPreferences
import com.dayker.airsearch.database.ProjectDatabase
import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.network.GeocodeService
import com.dayker.airsearch.utils.Constants.SHARED_PREFERENCES
import com.dayker.airsearch.utils.Utils.mapsRetrofitInit
import com.dayker.airsearch.utils.Utils.retrofitInit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<FlightsApiService> { retrofitInit().create(FlightsApiService::class.java) }
    single<GeocodeService> { mapsRetrofitInit().create(GeocodeService::class.java) }
    single {
        ProjectDatabase.getAppDatabaseInstance(androidContext().applicationContext)
    }
    single { get<ProjectDatabase>().flightDao() }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
}
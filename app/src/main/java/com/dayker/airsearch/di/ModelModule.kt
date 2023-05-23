package com.dayker.airsearch.di

import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.ui.info.InfoContract
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.ui.main.MainFragment
import org.koin.dsl.module

val modelModule = module {
    factory<MainContract.View> { MainFragment() }
    factory<InfoContract.View> { InfoActivity() }
//    factory<CoinFragmentView> { CoinFragment() }
//    factory<UserFragmentView> { UserFragment() }
}
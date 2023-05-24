package com.dayker.airsearch.di

import com.dayker.airsearch.ui.favorite.FavoriteContract
import com.dayker.airsearch.ui.favorite.FavoriteFragment
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.ui.info.InfoContract
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.ui.main.MainFragment
import com.dayker.airsearch.ui.search.SearchContract
import com.dayker.airsearch.ui.search.SearchFragment
import org.koin.dsl.module

val modelModule = module {
    factory<MainContract.View> { MainFragment() }
    factory<InfoContract.View> { InfoActivity() }
    factory<SearchContract.View> { SearchFragment() }
    factory<FavoriteContract.View> {FavoriteFragment()}
}
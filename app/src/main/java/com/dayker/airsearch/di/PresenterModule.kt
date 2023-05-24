package com.dayker.airsearch.di

import com.dayker.airsearch.ui.favorite.FavoriteContract
import com.dayker.airsearch.ui.favorite.FavoritePresenter
import com.dayker.airsearch.ui.info.InfoContract
import com.dayker.airsearch.ui.info.InfoPresenter
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.ui.main.MainPresenter
import com.dayker.airsearch.ui.search.SearchContract
import com.dayker.airsearch.ui.search.SearchPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory<MainContract.Presenter> {
        MainPresenter(
            apiService = get()
        )
    }
    factory<InfoContract.Presenter> {
        InfoPresenter(
            apiService = get(),
            dao = get()
        )
    }
    factory<FavoriteContract.Presenter> {
        FavoritePresenter(
            dao = get()
        )
    }
    factory<SearchContract.Presenter> {
        SearchPresenter(
            apiService = get()
        )
    }
}


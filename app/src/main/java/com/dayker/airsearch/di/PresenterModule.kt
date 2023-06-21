package com.dayker.airsearch.di

import com.dayker.airsearch.ui.favorite.FavoriteContract
import com.dayker.airsearch.ui.favorite.FavoritePresenter
import com.dayker.airsearch.ui.info.InfoContract
import com.dayker.airsearch.ui.info.InfoPresenter
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.ui.main.MainPresenter
import com.dayker.airsearch.ui.main.settings.SettingsContract
import com.dayker.airsearch.ui.main.settings.SettingsPresenter
import com.dayker.airsearch.ui.search.SearchContract
import com.dayker.airsearch.ui.search.SearchPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory<MainContract.Presenter> {
        MainPresenter(
            flightsApiService = get()
        )
    }
    factory<InfoContract.Presenter> {
        InfoPresenter(
            flightsApiService = get(),
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
            flightsApiService = get(),
            geocodeService = get()
        )
    }
    factory<SettingsContract.Presenter> {
        SettingsPresenter(
            flightsApiService = get()
        )
    }
}


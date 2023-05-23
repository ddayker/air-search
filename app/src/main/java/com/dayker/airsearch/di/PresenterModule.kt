package com.dayker.airsearch.di

import com.dayker.airsearch.ui.info.InfoContract
import com.dayker.airsearch.ui.info.InfoPresenter
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.ui.main.MainPresenter
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
}


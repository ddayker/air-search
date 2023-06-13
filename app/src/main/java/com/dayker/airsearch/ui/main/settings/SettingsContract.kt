package com.dayker.airsearch.ui.main.settings

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.Country

interface SettingsContract {

    interface View : BaseView {
        fun initRecyclerView()
        fun setCountries(countries: List<Country>)
        fun dataIsNotAvailable()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun downloadCountriesFromApi()
    }

}
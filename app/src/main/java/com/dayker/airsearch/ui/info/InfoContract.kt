package com.dayker.airsearch.ui.info

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.FlightInfo
import com.dayker.airsearch.model.ResponseX
import com.dayker.airsearch.ui.main.MainContract

interface InfoContract {

    interface View : BaseView {
        fun setContent(info: ResponseX)
        fun dataIsNotAvailable()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun downloadDataFromApi(icao: String)
    }

}
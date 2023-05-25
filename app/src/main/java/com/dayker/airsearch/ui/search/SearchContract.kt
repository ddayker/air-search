package com.dayker.airsearch.ui.search

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.ResponseX

interface SearchContract {

    interface View : BaseView {
        fun showFlightNotFound()
        fun showFlightInfo(flight: ResponseX)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun findFlight(icao: String)
    }
}
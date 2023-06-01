package com.dayker.airsearch.ui.search

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.FlightInfo

interface SearchContract {

    interface View : BaseView {
        fun showFlightNotFound()
        fun showFlightInfo(flight: FlightInfo)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun findFlight(icao: String)
    }
}
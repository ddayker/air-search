package com.dayker.airsearch.ui.info

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.model.FlightInfo

interface InfoContract {

    interface View : BaseView {
        fun setContent(flight: FlightInfo)
        fun setContent(flight: Flight)
        fun dataIsNotAvailable()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun addToFavorite(flight: Flight)
        abstract fun deleteFromFavorite(icao: String)
        abstract fun checkForFavoriteAndDownload(icao: String): Boolean
    }
}
package com.dayker.airsearch.ui.info

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.model.ResponseX

interface InfoContract {

    interface View : BaseView {
        fun setContent(flight: ResponseX)
        fun setContent(flight: Flight)
        fun dataIsNotAvailable()
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun addToFavorite(flight : Flight)
        abstract fun deleteFromFavorite(icao: String)
        abstract fun checkForFavoriteAndDownload(icao: String): Boolean
    }
}
package com.dayker.airsearch.ui.favorite

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.model.Response

interface FavoriteContract {

    interface View : BaseView {
        fun initRecyclerView()
        fun setContent(flights: List<Flight>)
    }

    abstract class Presenter : BasePresenter<View>() {

        abstract fun downloadDataFromDB()
    }

}
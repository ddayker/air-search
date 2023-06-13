package com.dayker.airsearch.ui.main

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.ActualFlight
import com.dayker.airsearch.utils.Constants.WITHOUT_REGION

interface MainContract {

    interface View : BaseView {
        fun initRecyclerView()
        fun setContent(flights: List<ActualFlight>)
        fun setRemoteText(text: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun downloadDataFromApi(region: String = WITHOUT_REGION)
        abstract fun getRemoteMessage(key: String)
    }
}
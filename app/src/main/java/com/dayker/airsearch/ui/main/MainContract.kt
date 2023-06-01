package com.dayker.airsearch.ui.main

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.ActualFlight

interface MainContract {

    interface View : BaseView {
        fun initRecyclerView()
        fun setContent(flights: List<ActualFlight>)
        fun setRemoteText(text: String)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun downloadDataFromApi()
        abstract fun getRemoteMessage(key: String)
    }

}
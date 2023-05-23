package com.dayker.airsearch.ui.main

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.base.BaseView
import com.dayker.airsearch.model.Response

interface MainContract {

    interface View : BaseView {
        fun initRecyclerView()
        fun setContent(flights: List<Response>)
    }

    abstract class Presenter : BasePresenter<View>() {
        abstract fun downloadDataFromApi()
    }

}
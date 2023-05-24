package com.dayker.airsearch.ui.favorite

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.database.dao.FlightDao
import com.dayker.airsearch.database.entity.Flight
import kotlinx.coroutines.launch

class FavoritePresenter(
    private val dao: FlightDao
) : FavoriteContract.Presenter() {

    override fun downloadDataFromDB() {
        view?.initRecyclerView()
        coroutineScope.launch {
            val flight = dao.getAll()
            println(flight)
            view?.setContent(flight)
        }
    }
}


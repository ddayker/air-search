package com.dayker.airsearch.ui.favorite

import com.dayker.airsearch.database.dao.FlightDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritePresenter(
    private val dao: FlightDao
) : FavoriteContract.Presenter() {

    override fun downloadDataFromDB() {
        view?.initRecyclerView()
        coroutineScope.launch {
            val flight = dao.getAll()
            withContext(Dispatchers.Main) {
                view?.setContent(flight)
            }
        }
    }
}


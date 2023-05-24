package com.dayker.airsearch.ui.info

import com.dayker.airsearch.database.dao.FlightDao
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoPresenter(
    private val apiService: ApiService,
    private val dao: FlightDao
) : InfoContract.Presenter() {

    // if flight is already in favorite return true and download from db
    // else download data from api request
    override fun checkForFavoriteAndDownload(icao: String): Boolean {
        val flight = dao.getFlight(icao)
        return if (flight != null) {
            view?.setContent(flight)
            true
        } else {
            downloadDataFromApi(icao)
            false
        }
    }

    private fun downloadDataFromApi(icao: String) {

        coroutineScope.launch {
            try {
                val response =
                    apiService.getFlightInfo(
                        icao,
                        API_KEY
                    )
                withContext(Dispatchers.Main) {
                    view?.setContent(response.response)
                }
            } catch (e: Exception) {
                println(javaClass.simpleName + e.message)
                withContext(Dispatchers.Main) {
                    view?.dataIsNotAvailable()
                }
            }
        }
    }

    override fun addToFavorite(flight: Flight) {
        coroutineScope.launch {
            dao.insert(flight)
        }
    }

    override fun deleteFromFavorite(icao: String) {
        coroutineScope.launch {
            dao.deleteFlight(icao)
        }
    }
}

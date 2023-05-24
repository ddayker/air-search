package com.dayker.airsearch.ui.search

import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPresenter(
    private val apiService: ApiService
) : SearchContract.Presenter() {
    override fun findFlight(icao: String) {
        coroutineScope.launch {
            try {
                val response =
                    apiService.getFlightInfo(
                        icao,
                        Constants.API_KEY
                    )
                withContext(Dispatchers.Main) {
                    view?.showFlightInfo(response.response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showFlightNotFound()
                }
            }
        }
    }
}
package com.dayker.airsearch.ui.info

import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoPresenter(
    private val apiService: ApiService
) : InfoContract.Presenter() {

    override fun downloadDataFromApi(icao: String) {
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
                withContext(Dispatchers.Main){
                view?.dataIsNotAvailable()}
            }
        }
    }
}
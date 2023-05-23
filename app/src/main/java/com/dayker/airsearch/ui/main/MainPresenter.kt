package com.dayker.airsearch.ui.main

import com.dayker.airsearch.base.BasePresenter
import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.ApiUtils
import com.dayker.airsearch.utils.Constants
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MainPresenter(
    private val apiService: ApiService
) : MainContract.Presenter() {

    override fun downloadDataFromApi(){
        view?.initRecyclerView()
        coroutineScope.launch {
            try {
                val response =
                    apiService.getFlights(
                        Constants.API_KEY
                    )
                withContext(Dispatchers.Main) {
                   view?.setContent(response.response)
                }
            } catch (e: Exception) {
                println(javaClass.simpleName +  e.message)
            }
        }
    }
}
package com.dayker.airsearch.ui.main

import com.dayker.airsearch.network.FlightsApiService
import com.dayker.airsearch.utils.Constants
import com.dayker.airsearch.utils.Constants.WITHOUT_REGION
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.*

class MainPresenter(
    private val flightsApiService: FlightsApiService
) : MainContract.Presenter() {

    override fun downloadDataFromApi(region: String) {
        view?.initRecyclerView()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val response = if (region != WITHOUT_REGION) {
                    flightsApiService.getFlightsWithRegion(
                        Constants.API_KEY,
                        region
                    )
                } else {
                    flightsApiService.getFlights(
                        Constants.API_KEY
                    )
                }
                withContext(Dispatchers.Main) {
                    view?.setContent(response.response)
                }
            } catch (e: Exception) {
                println(javaClass.simpleName + e.message)
            }
            scope.cancel()
        }
    }

    override fun getRemoteMessage(key: String) {
        val mFirebaseConfig = FirebaseRemoteConfig.getInstance()
        val mFirebaseConfigSettings = FirebaseRemoteConfigSettings.Builder().build()
        mFirebaseConfig.setConfigSettingsAsync(mFirebaseConfigSettings)
        mFirebaseConfig.fetch(0)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    mFirebaseConfig.activate()
                    view?.setRemoteText(mFirebaseConfig.getString(key))
                }
            }
    }
}
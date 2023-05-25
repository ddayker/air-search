package com.dayker.airsearch.ui.main

import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.Constants
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private val apiService: ApiService
) : MainContract.Presenter() {

    override fun downloadDataFromApi() {
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
                println(javaClass.simpleName + e.message)
            }
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
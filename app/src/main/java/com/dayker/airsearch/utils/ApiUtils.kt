package com.dayker.airsearch.utils

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    fun retrofitInit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    fun isConnectionError(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return true
        return false
    }

//    fun isConnectedToNetwork(context: Context): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
//        val network = connectivityManager?.activeNetwork
//        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
//
//        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
//    }

}


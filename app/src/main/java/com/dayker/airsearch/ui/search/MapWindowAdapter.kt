package com.dayker.airsearch.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.dayker.airsearch.databinding.MarkerInfoBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MapWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val binding = MarkerInfoBinding.inflate(LayoutInflater.from(context))
        binding.tvRoute.text = marker.title
        binding.tvICAO.text = marker.snippet
        return binding.root
    }
}


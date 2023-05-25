package com.dayker.airsearch.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dayker.airsearch.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MapWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    @SuppressLint("InflateParams")
    var window: View = LayoutInflater.from(context).inflate(R.layout.marker_info, null)

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val rout = window.findViewById<TextView>(R.id.tvRoute)
        val icao = window.findViewById<TextView>(R.id.tvICAO)
        rout.text = marker.title
        icao.text = marker.snippet
        return window
    }
}


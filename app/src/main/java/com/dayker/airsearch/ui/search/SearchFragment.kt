package com.dayker.airsearch.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentSearchBinding
import com.dayker.airsearch.model.ResponseX
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.Constants
import com.dayker.airsearch.utils.Constants.EMPTY_STRING
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class SearchFragment : Fragment(), SearchContract.View {

    private var binding: FragmentSearchBinding? = null
    private val presenter: SearchContract.Presenter by inject()
    private lateinit var googleMap: GoogleMap
    private var planeMarker: Marker? = null
    private var icaoNumber = EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }

        binding?.btnSearch?.setOnClickListener {
            val icaoCode = binding?.editTextSearch?.text.toString()
            presenter.findFlight(icao = icaoCode)

            mapFragment?.getMapAsync { map ->
                googleMap = map
                setupMap()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showFlightNotFound() {
        binding?.notFoundMessage?.visibility = View.VISIBLE
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun showFlightInfo(flight: ResponseX) {
        binding?.notFoundMessage?.visibility = View.GONE
        val latitude = flight.lat
        val longitude = flight.lng
        val rout = "${flight.depCity} - ${flight.arrCity}"
        icaoNumber = flight.flightIcao

        val planePosition = LatLng(latitude, longitude)

        if (planeMarker == null) {
            val markerOptions = MarkerOptions().position(planePosition).title(rout).snippet(icaoNumber)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            planeMarker = googleMap.addMarker(markerOptions)
        } else {
            planeMarker?.position = planePosition
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(planePosition))
    }

    private fun setupMap() {

        val markerInfoWindowAdapter = MapWindowAdapter(requireContext())
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)


        googleMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            val intent = Intent(requireContext(), InfoActivity::class.java)
            intent.putExtra(Constants.ICAO_KEY, icaoNumber)
            startActivity(intent)
        }
    }
}







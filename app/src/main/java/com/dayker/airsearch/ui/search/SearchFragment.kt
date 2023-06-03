package com.dayker.airsearch.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.FragmentSearchBinding
import com.dayker.airsearch.model.FlightInfo
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.ApiUtils.isConnectionError
import com.dayker.airsearch.utils.Constants
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
    private var icaoNumber = R.string.empty_string.toString()

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
        initMap(mapFragment)
        binding?.btnSearch?.setOnClickListener {
            if (isConnectionError(requireContext())) {
                showConnectionError()
            } else {
                searchFlight(mapFragment)
            }
        }
    }

    override fun showFlightNotFound() {
        binding?.notFoundMessage?.text = getString(R.string.is_not_detected)
        binding?.notFoundMessage?.visibility = View.VISIBLE
    }

    private fun showConnectionError() {
        binding?.notFoundMessage?.text = getString(R.string.no_internet_connection)
        binding?.notFoundMessage?.visibility = View.VISIBLE
    }

    private fun initMap(mapFragment: SupportMapFragment?) {
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }
    }

    private fun searchFlight(mapFragment: SupportMapFragment?) {
        val icaoCode = binding?.editTextSearch?.text.toString()
        presenter.findFlight(icao = icaoCode)
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }
    }

    override fun showFlightInfo(flight: FlightInfo) {
        binding?.notFoundMessage?.visibility = View.GONE
        val latitude = flight.lat
        val longitude = flight.lng
        val rout = "${flight.depCity} - ${flight.arrCity}"
        icaoNumber = flight.flightIcao

        val planePosition = LatLng(latitude, longitude)

        if (planeMarker == null) {
            val markerOptions =
                MarkerOptions().position(planePosition).title(rout).snippet(icaoNumber)
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

        googleMap.setOnInfoWindowClickListener {
            val intent = Intent(requireContext(), InfoActivity::class.java)
            intent.putExtra(Constants.ICAO_KEY, icaoNumber)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}







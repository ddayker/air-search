package com.dayker.airsearch.ui.info

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dayker.airsearch.R
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.ActivityInfoBinding
import com.dayker.airsearch.model.info.FlightInfo
import com.dayker.airsearch.ui.MainActivity
import com.dayker.airsearch.ui.search.SearchFragment
import com.dayker.airsearch.utils.Constants.ICAO_KEY
import com.dayker.airsearch.utils.Constants.SHOW_ON_MAP_KEY
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class InfoActivity : AppCompatActivity(), InfoContract.View {

    private lateinit var binding: ActivityInfoBinding
    private val presenter: InfoContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attachView(this)

        val icao = intent.getStringExtra(ICAO_KEY)
        if (icao != null) {
            if (presenter.checkForFavoriteAndDownload(icao)) {
                binding.favoriteButton.isChecked = true
            }
        }

        with(binding) {
            backButton.setOnClickListener {
                finish()
            }
            imgBtnShowOnMap.setOnClickListener {
                if (icao != null) {
                    showFlightOnMap(icao)
                }
            }
            btnShowOnMap.setOnClickListener {
                if (icao != null) {
                    showFlightOnMap(icao)
                }
            }
            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    presenter.addToFavorite(initNewFavoriteFlight())
                    makeSnackbar(getString(R.string.add_favorite_message))
                } else {
                    presenter.deleteFromFavorite(titleICAO.text.toString())
                    makeSnackbar(getString(R.string.delete_favorite_message))
                }
            }
        }
    }

    override fun setContent(flight: FlightInfo) {
        with(binding) {
            titleICAO.text = flight.flightIcao
            company.text = flight.airlineName
            status.text = flight.status
            tvCity1.text = flight.depCity
            tvAirport1.text = flight.depName
            tvTime1.text = flight.depTime
            tvRealTime1.text = flight.depActualTime
            tvCity2.text = flight.arrCity
            tvAirport2.text = flight.arrName
            tvTime2.text = flight.arrTime
            tvRealTime2.text = flight.arrActualTime
            if (company.text.isEmpty()) {
                company.text = getString(R.string.no_information)
            }
            if (tvCity1.text.isEmpty()) {
                tvCity1.text = getString(R.string.no_information)
            }
            if (tvCity2.text.isEmpty()) {
                tvCity2.text = getString(R.string.no_information)
            }
        }
    }

    override fun setContent(flight: Flight) {
        with(binding) {
            titleICAO.text = flight.icao
            company.text = flight.company
            tvCity1.text = flight.depCity
            tvAirport1.text = flight.depAirport
            tvTime1.text = flight.depTime
            tvCity2.text = flight.arrCity
            tvAirport2.text = flight.arrAirport
            tvTime2.text = flight.arrTime
            // if the flight is finished-the functionality of displaying on the map is not available
            if (tvTime2.text.isEmpty() || isDateExpired(tvTime2.text.toString())) {
                disableShowOnMap()
            }
        }
    }

    override fun dataIsNotAvailable() {
        with(binding) {
            company.text = R.string.data_is_not_available.toString()
        }
    }

    private fun initNewFavoriteFlight(): Flight {
        with(binding) {
            return Flight(
                icao = titleICAO.text.toString(),
                company = company.text.toString(),
                depCity = tvCity1.text.toString(),
                depAirport = tvAirport1.text.toString(),
                depTime = tvTime1.text.toString(),
                arrCity = tvCity2.text.toString(),
                arrAirport = tvAirport2.text.toString(),
                arrTime = tvTime2.text.toString()
            )
        }
    }

    private fun disableShowOnMap() {
        binding.btnShowOnMap.visibility = View.GONE
        binding.imgBtnShowOnMap.isEnabled = false

    }

    private fun isDateExpired(dateTimeString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateTime = dateFormat.parse(dateTimeString)
        val currentTime = Calendar.getInstance().time
        return dateTime!!.before(currentTime)
    }

    private fun showFlightOnMap(icao: String) {
        SearchFragment.searchIcao = icao
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SHOW_ON_MAP_KEY, true)
        startActivity(intent)
    }

    private fun makeSnackbar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
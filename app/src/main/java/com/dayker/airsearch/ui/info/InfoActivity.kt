package com.dayker.airsearch.ui.info

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.ColumnInfo
import com.dayker.airsearch.R
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.ActivityInfoBinding
import com.dayker.airsearch.model.ResponseX
import com.dayker.airsearch.ui.main.MainContract
import com.dayker.airsearch.utils.Constants.ICAO_KEY
import org.koin.android.ext.android.inject

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
            presenter.downloadDataFromApi(icao)
        }

        with(binding) {
            backButton.setOnClickListener {
                finish()
            }
            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    presenter.addToFavorite(initNewFlight())
                } else {
                    presenter.deleteFromFavorite(titleICAO.text.toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setContent(info: ResponseX) {
        with(binding) {
            titleICAO.text = info.flightIcao
            company.text = info.airlineName
            status.text = info.status
            tvCity1.text = info.depCity
            tvAirport1.text = info.depName
            tvTime1.text = info.depTime
            tvRealTime1.text = info.depActualTime
            tvCity2.text = info.arrCity
            tvAirport2.text = info.arrName
            tvTime2.text = info.arrTime
            tvRealTime2.text = info.arrActualTime
        }
    }


    override fun dataIsNotAvailable() {
        with(binding) {
            company.text = "Private Jet"
            status.text = "information is not available"
        }
    }

    fun initNewFlight(): Flight {
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


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
package com.dayker.airsearch.ui.info

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dayker.airsearch.R
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

        binding.backButton.setOnClickListener {
            finish()
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
                tvTerminal1.text = "Terminal ${info.depTerminal}"
                tvTime1.text = info.depTime
                tvRealTime1.text = info.depActualTime
                tvCity2.text = info.arrCity
                tvAirport2.text = info.arrName
                tvTime2.text = info.arrTime
                tvRealTime2.text = info.arrActualTime
            }
        }


    override fun dataIsNotAvailable(){
        with(binding) {
            company.text = "Private Jet"
            status.text = "information is not available"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
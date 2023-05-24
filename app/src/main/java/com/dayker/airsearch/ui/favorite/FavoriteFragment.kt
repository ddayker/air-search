package com.dayker.airsearch.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.R
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.FragmentFavoriteBinding
import com.dayker.airsearch.databinding.FragmentMainBinding
import com.dayker.airsearch.model.Response
import com.dayker.airsearch.ui.main.MainAdapter
import com.dayker.airsearch.ui.main.MainContract
import org.koin.android.ext.android.inject

class FavoriteFragment : Fragment(), FavoriteContract.View {

    private val presenter: FavoriteContract.Presenter by inject()
    private var binding: FragmentFavoriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.downloadDataFromDB()
    }

    override fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rv?.layoutManager = layoutManager
    }

    override fun setContent(flights: List<Flight>) {
        val adapter = FavoriteAdapter(flights)
        binding?.rv?.adapter = adapter
        println(flights)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}
package com.dayker.airsearch.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.FragmentFavoriteBinding
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

    override fun onResume() {
        super.onResume()
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
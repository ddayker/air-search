package com.dayker.airsearch.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.ItemFavoriteBinding
import com.dayker.airsearch.databinding.ItemFlightBinding
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.Constants

class FavoriteAdapter(private val dataSet: List<Flight>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Flight) {
            with(binding) {
                tvCity1.text = item.depCity
                tvCity2.text = item.arrCity
                tvCompany.text = item.company
                tvDate.text = "${item.depTime} - ${item.arrTime}"
                tvFlightNumber.text = item.icao
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, InfoActivity::class.java)
            intent.putExtra(Constants.ICAO_KEY, dataSet[position].icao)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size

}
package com.dayker.airsearch.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dayker.airsearch.database.entity.Flight
import com.dayker.airsearch.databinding.ItemFavoriteBinding
import com.dayker.airsearch.ui.DiffCallback
import com.dayker.airsearch.ui.info.InfoActivity
import com.dayker.airsearch.utils.Constants

class FavoriteAdapter(private var dataSet: List<Flight>) :
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
                arrDate.text = item.arrTime
                depDate.text = item.depTime
                tvFlightNumber.text = item.icao
            }
        }
    }

    fun updateData(newData: List<Flight>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffCallback(
                oldData = dataSet,
                newData = newData,
                areItemsTheSame = { oldItem, newItem ->
                    oldItem.icao == newItem.icao
                },
                areContentsTheSame = { oldItem, newItem ->
                    oldItem == newItem
                }
            )
        )
        dataSet = newData
        diffResult.dispatchUpdatesTo(this)
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
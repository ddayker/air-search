package com.dayker.airsearch.ui.main.settings

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dayker.airsearch.R
import com.dayker.airsearch.databinding.ItemCountryBinding
import com.dayker.airsearch.model.Country
import com.dayker.airsearch.ui.DiffCallback
import com.dayker.airsearch.utils.Constants.REGION_KEY
import com.google.android.material.snackbar.Snackbar


class CountriesAdaptor(
    private var dataSet: List<Country>,
    val sharedPreferences: SharedPreferences
) :
    RecyclerView.Adapter<CountriesAdaptor.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            with(binding) {
                tvCountry.text = item.name
            }
        }
    }

    fun updateData(newData: List<Country>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffCallback(
                oldData = dataSet,
                newData = newData,
                areItemsTheSame = { oldItem, newItem ->
                    oldItem.name == newItem.name
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
        val binding = ItemCountryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.countries_animation)
        holder.itemView.startAnimation(animation)
        holder.itemView.setOnClickListener {
            sharedPreferences.edit().putString(REGION_KEY, dataSet[holder.adapterPosition].code)
                .apply()
            val message = holder.itemView.context.getString(R.string.region_changed)
            Snackbar.make(
                holder.itemView,
                "$message ${dataSet[holder.adapterPosition].code}",
                Snackbar.LENGTH_SHORT
            ).show()
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.action_settingsFragment_to_mainFragment)
        }

    }

    override fun getItemCount() = dataSet.size
}
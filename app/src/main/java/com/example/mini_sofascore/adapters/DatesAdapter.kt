package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.databinding.DateItemBinding

class DatesAdapter(private val dates: List<String>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<DatesAdapter.DateViewHolder>() {


    class DateViewHolder(private val binding: DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: String) {
            binding.date.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder(
            DateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(dates[position])
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }


    class OnClickListener(val clickListener: (date: String?) -> Unit) {
        fun onClick(date: String) = clickListener(date)
    }

}
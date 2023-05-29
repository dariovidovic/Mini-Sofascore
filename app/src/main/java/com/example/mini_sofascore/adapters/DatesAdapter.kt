package com.example.mini_sofascore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.databinding.DateItemBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DatesAdapter(private val dates: List<String>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<DatesAdapter.DateViewHolder>() {



    class DateViewHolder(private val binding: DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: String) {

            val stringFormat = SimpleDateFormat("yyyy-MM-dd")
            val dayFormat = SimpleDateFormat("E")
            val dateFormat = SimpleDateFormat("dd.MM.")

            val data = stringFormat.parse(date)

            binding.day.text = dayFormat.format(data)
            binding.date.text = dateFormat.format(data)
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
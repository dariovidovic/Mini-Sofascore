package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mini_sofascore.EventDetailActivity
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.databinding.MatchesItemBinding
import com.example.mini_sofascore.utils.MatchesViewHolder


class TournamentMatchesAdapter :
    PagingDataAdapter<Match, MatchesViewHolder>(MatchesDiffCallback()) {

    class MatchesDiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        getItem(position)?.also {
            holder.bind(it)
        }

        holder.itemView.setOnClickListener {
            getItem(position)?.also {
                it.let {
                    EventDetailActivity.start(holder.itemView.context, it.id)
                }
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder(
            MatchesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}
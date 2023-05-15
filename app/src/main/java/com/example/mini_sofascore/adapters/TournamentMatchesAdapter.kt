package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.EventDetailActivity
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.databinding.MatchesItemBinding
import com.example.mini_sofascore.databinding.TournamentRoundItemBinding
import com.example.mini_sofascore.utils.Helper


class TournamentMatchesAdapter :
    PagingDataAdapter<Match, TournamentMatchesAdapter.MatchesViewHolder>(MatchesDiffCallback()) {


    class MatchesViewHolder(private val binding: MatchesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match?) {
            binding.homeTeamLogo.load(Helper.getTeamImageUrl(match?.homeTeam?.id))
            binding.awayTeamLogo.load(Helper.getTeamImageUrl(match?.awayTeam?.id))
            if (match?.status == "notstarted") {
                binding.matchStatus.text = ""
            } else binding.matchStatus.text = "FT"

            binding.homeTeamScore.text = match?.homeScore?.total?.toString()
            binding.awayTeamScore.text = match?.awayScore?.total?.toString()

            binding.homeTeamName.text = match?.homeTeam?.name
            binding.awayTeamName.text = match?.awayTeam?.name
            binding.matchTime.text = match?.startDate?.subSequence(11, 16)
        }
    }

    class TournamentRoundViewHolder(private val binding: TournamentRoundItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tournamentRound: String) {
            binding.tournamentRound.text = tournamentRound
        }
    }


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
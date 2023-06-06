package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.EventDetailActivity
import com.example.mini_sofascore.TournamentDetailsActivity
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.databinding.LeaguesItemBinding
import com.example.mini_sofascore.databinding.MatchesItemBinding
import com.example.mini_sofascore.databinding.TournamentItemBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.MatchesViewHolder
import kotlin.IllegalArgumentException

private const val TYPE_TOURNAMENT = 0
private const val TYPE_EVENT = 1
private const val TYPE_LEAGUES = 2

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var matches: MutableList<Any?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_EVENT -> {
                return MatchesViewHolder(
                    MatchesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_TOURNAMENT -> {
                return TournamentViewHolder(
                    TournamentItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_LEAGUES -> {
                return LeaguesViewHolder(
                    LeaguesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }


    }

    override fun getItemViewType(position: Int) = when (matches[position]) {
        is Match -> TYPE_EVENT
        is String -> TYPE_TOURNAMENT
        is Tournament -> TYPE_LEAGUES
        else -> throw IllegalArgumentException()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_EVENT) {
            (holder as MatchesViewHolder).bind(matches[position] as Match?)
        } else if (getItemViewType(position) == TYPE_TOURNAMENT) {
            (holder as TournamentViewHolder).bind(
                matches[position] as String,
                matches[position + 1] as Match?
            )
        } else
            (holder as LeaguesViewHolder).bind(matches[position] as Tournament)

        holder.itemView.setOnClickListener {
            val match = matches.getOrNull(position) as? Match
            val tournament = matches.getOrNull(position) as? String
            val league = matches.getOrNull(position) as? Tournament

            league?.let {
                val currentLeague = matches[position] as Tournament?
                TournamentDetailsActivity.start(holder.itemView.context, currentLeague?.id ?: 1)
            }

            tournament?.let {
                val currentTournament = matches[position + 1] as Match?
                TournamentDetailsActivity.start(
                    holder.itemView.context,
                    currentTournament?.tournament?.id ?: 1
                )
            }
            match?.let {
                EventDetailActivity.start(holder.itemView.context, match.id)
            }

        }

    }

    override fun getItemCount(): Int {
        return matches.size
    }


    class TournamentViewHolder(private val binding: TournamentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tournament: String, match: Match?) {
            binding.tournamentLogo.load(Helper.getTournamentImageUrl(match?.tournament?.id))
            binding.tournamentName.text = tournament
            binding.countryName.text = match?.tournament?.country?.name
        }
    }

    class LeaguesViewHolder(private val binding: LeaguesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tournament: Tournament) {
            binding.tournamentLogo.load(Helper.getTournamentImageUrl(tournament.id))
            binding.leagueName.text = tournament.name
        }

    }

    fun setData(matches: List<Any?>) {
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }
}
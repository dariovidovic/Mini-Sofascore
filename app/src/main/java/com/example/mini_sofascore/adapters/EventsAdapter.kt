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
import java.lang.IllegalArgumentException

private const val TYPE_TOURNAMENT = 0
private const val TYPE_EVENT = 1
private const val TYPE_LEAGUES = 2

class EventsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var matches: MutableList<Any?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_EVENT) {
            MatchesViewHolder(
                MatchesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else if (viewType == TYPE_TOURNAMENT)
            TournamentViewHolder(
                TournamentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            LeaguesViewHolder(
                LeaguesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

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
            tournament?.let {
                val currentTournament = matches[position+1] as Match?
                TournamentDetailsActivity.start(holder.itemView.context, currentTournament?.tournament?.id?:1 )
            }
            match?.let {
                EventDetailActivity.start(holder.itemView.context, match.id)
            }

        }

    }

    override fun getItemCount(): Int {
        return matches.size
    }

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
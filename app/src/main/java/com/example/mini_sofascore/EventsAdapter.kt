package com.example.mini_sofascore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.data.Matches
import com.example.mini_sofascore.databinding.MatchesItemBinding
import com.example.mini_sofascore.databinding.TournamentItemBinding
import java.lang.IllegalArgumentException

private const val TYPE_TOURNAMENT = 0
private const val TYPE_EVENT = 1
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
        } else
            TournamentViewHolder(
                TournamentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }

    override fun getItemViewType(position: Int) = when (matches[position]) {
        is Matches -> TYPE_EVENT
        is String -> TYPE_TOURNAMENT
        else -> throw IllegalArgumentException()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == TYPE_EVENT) {
            (holder as MatchesViewHolder).bind(matches[position] as Matches?)
        } else {
            (holder as TournamentViewHolder).bind(matches[position] as String, matches[position+1] as Matches?)
        }


    }

    override fun getItemCount(): Int {
        return matches.size
    }

    class MatchesViewHolder(private val binding: MatchesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Matches?) {
            binding.homeTeamLogo.load("https://academy.dev.sofascore.com/team/${match?.homeTeam?.id}/image")
            binding.awayTeamLogo.load("https://academy.dev.sofascore.com/team/${match?.awayTeam?.id}/image")
            if (match?.status == "notstarted") {
                binding.matchStatus.text = ""
            }
            else binding.matchStatus.text = "FT"
            if (match?.homeScore?.total == null) {
                binding.homeTeamScore.text = ""
            }
            else binding.homeTeamScore.text = match.homeScore.total.toString()

            if (match?.awayScore?.total == null) {
                binding.awayTeamScore.text = ""
            }
            else binding.awayTeamScore.text = match.awayScore.total.toString()

            binding.homeTeamName.text = match?.homeTeam?.name
            binding.awayTeamName.text = match?.awayTeam?.name
            binding.matchTime.text = match?.startDate?.subSequence(11, 16)
        }
    }

    class TournamentViewHolder(private val binding: TournamentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tournament: String, match: Matches?) {
            binding.tournamentLogo.load("https://academy.dev.sofascore.com/tournament/${match?.tournament?.id}/image")
            binding.tournamentName.text = tournament
            binding.countryName.text = match?.tournament?.country?.name
        }
    }

    fun setData(matches: List<Any?>) {
        this.matches.clear()
        this.matches.addAll(matches)
        notifyDataSetChanged()
    }
}
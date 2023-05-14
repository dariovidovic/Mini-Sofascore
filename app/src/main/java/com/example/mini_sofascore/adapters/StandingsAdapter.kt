package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.data.TeamStanding
import com.example.mini_sofascore.databinding.FootballStandingItemBinding


class StandingsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tournamentStandings: MutableList<TeamStanding?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FootballStandingViewHolder(
            FootballStandingItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FootballStandingViewHolder).bind(tournamentStandings[position] as TeamStanding)
    }

    override fun getItemCount(): Int {
        return tournamentStandings.size
    }

    fun setData(tournamentStandings: List<TeamStanding?>) {
        this.tournamentStandings.clear()
        this.tournamentStandings.addAll(tournamentStandings)
        notifyDataSetChanged()
    }

    class FootballStandingViewHolder(private val binding: FootballStandingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentStandings: TeamStanding) {
            binding.run {
                teamPosition.text = (position + 1).toString()
                teamName.text = tournamentStandings.team.name
                teamMatchesPlayed.text = tournamentStandings.played.toString()
                teamMatchesWon.text = tournamentStandings.wins.toString()
                teamMatchesDrew.text = tournamentStandings.draws.toString()
                teamMatchesLost.text = tournamentStandings.losses.toString()
                teamTotalGoals.text =
                    "${tournamentStandings.scoresFor}:${tournamentStandings.scoresAgainst}"
                teamTotalPoints.text = tournamentStandings.points.toString()
            }
        }

    }
}
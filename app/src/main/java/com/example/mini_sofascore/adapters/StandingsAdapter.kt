package com.example.mini_sofascore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.data.TeamStanding
import com.example.mini_sofascore.databinding.AmericanFootballStandingItemBinding
import com.example.mini_sofascore.databinding.BasketballStandingItemBinding
import com.example.mini_sofascore.databinding.FootballStandingItemBinding
import com.example.mini_sofascore.utils.Sport

class StandingsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tournamentStandings: MutableList<TeamStanding?> = arrayListOf()
    private var sportName: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (sportName) {
            Sport.FOOTBALL -> {
                return FootballStandingViewHolder(
                    FootballStandingItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            Sport.BASKETBALL -> {
                return BasketballStandingViewHolder(
                    BasketballStandingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            Sport.AMERICAN_FOOTBALL -> {
                return AmericanFootballStandingViewHolder(
                    AmericanFootballStandingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (sportName) {
            Sport.FOOTBALL -> (holder as FootballStandingViewHolder).bind(tournamentStandings[position] as TeamStanding)
            Sport.BASKETBALL -> (holder as BasketballStandingViewHolder).bind(tournamentStandings[position] as TeamStanding)
            Sport.AMERICAN_FOOTBALL -> (holder as AmericanFootballStandingViewHolder).bind(
                tournamentStandings[position] as TeamStanding
            )
            else -> throw IllegalArgumentException()

        }
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

    class AmericanFootballStandingViewHolder(private val binding: AmericanFootballStandingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentStandings: TeamStanding) {
            binding.run {
                teamPosition.text = (position + 1).toString()
                teamName.text = tournamentStandings.team.name
                teamMatchesPlayed.text = tournamentStandings.played.toString()
                teamMatchesWon.text = tournamentStandings.wins.toString()
                teamMatchesDrew.text = tournamentStandings.draws.toString()
                teamMatchesLost.text = tournamentStandings.losses.toString()
                teamPercentage.text = String.format("%.3f", tournamentStandings.percentage)
            }
        }

    }


    class BasketballStandingViewHolder(private val binding: BasketballStandingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentStandings: TeamStanding) {
            binding.run {
                teamPosition.text = (position + 1).toString()
                teamName.text = tournamentStandings.team.name
                teamMatchesPlayed.text = tournamentStandings.played.toString()
                teamMatchesWon.text = tournamentStandings.wins.toString()
                teamMatchesLost.text = tournamentStandings.losses.toString()
                teamDiff.text =
                    (tournamentStandings.scoresFor - tournamentStandings.scoresAgainst).toString()
                teamPercentage.text = String.format("%.3f", tournamentStandings.percentage)
            }
        }

    }

    fun setSportName(sportName: String?) {
        this.sportName = sportName
        notifyDataSetChanged()
    }


}
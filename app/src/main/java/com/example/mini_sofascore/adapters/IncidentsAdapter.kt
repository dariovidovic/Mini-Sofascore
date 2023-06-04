package com.example.mini_sofascore.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.PlayerDetailsActivity
import com.example.mini_sofascore.R
import com.example.mini_sofascore.data.Incidents
import com.example.mini_sofascore.databinding.BasketballScoreItemBinding
import com.example.mini_sofascore.databinding.FoulItemBinding
import com.example.mini_sofascore.databinding.PeriodItemBinding
import com.example.mini_sofascore.databinding.ScoreItemBinding
import com.example.mini_sofascore.utils.Goal
import com.example.mini_sofascore.utils.Sport
import com.example.mini_sofascore.utils.Type


private const val TYPE_FOUL = 0
private const val TYPE_SCORE = 1
private const val TYPE_PERIOD = 2

class IncidentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var incidents: MutableList<Incidents?> = arrayListOf()
    private var sport: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_FOUL -> {
                FoulViewHolder(
                    FoulItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            TYPE_SCORE -> {
               return when (sport) {
                    Sport.FOOTBALL -> {
                        ScoreViewHolder(
                            ScoreItemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        )
                    }
                    Sport.BASKETBALL -> {
                        BasketballViewHolder(
                            BasketballScoreItemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        )
                    }
                    Sport.AMERICAN_FOOTBALL -> {
                        ScoreViewHolder(
                            ScoreItemBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        )
                    }

                    else -> {
                        throw IllegalArgumentException()
                    }
                }


            }

            TYPE_PERIOD -> {
                PeriodViewHolder(
                    PeriodItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }


    override fun getItemViewType(position: Int) = when (incidents[position]?.type) {
        Type.GOAL -> TYPE_SCORE
        Type.FOUL -> TYPE_FOUL
        Type.PERIOD -> TYPE_PERIOD
        else -> throw IllegalArgumentException()
    }

    class PeriodViewHolder(private val binding: PeriodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(incident: Incidents, sport: String) {
            binding.periodStatus.text = incident.text
        }
    }

    class FoulViewHolder(private val binding: FoulItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(incident: Incidents, sport: String) {
            binding.run {
                if (incident.color == "yellow")
                    incidentIcon.setImageResource(R.drawable.ic_icon_yellow_card)
                else
                    incidentIcon.setImageResource(R.drawable.ic_icon_red_card)

                incidentTime.text = incident.time.toString()
                incidentPlayerName.text = incident.player.name
            }
        }
    }

    class ScoreViewHolder(private val binding: ScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(incident: Incidents, sport: String) {
            binding.run {
                homeTeamScore.text = incident.homeScore.toString()
                awayTeamScore.text = incident.awayScore.toString()
                when (sport) {
                    Sport.FOOTBALL -> {
                        incidentTime.text = incident.time.toString()
                        incidentPlayerName.text = incident.player.name
                        incidentIcon.setImageResource(R.drawable.ic_icon_goal)
                    }
                    Sport.AMERICAN_FOOTBALL -> {
                        incidentPlayerName.text = incident.player.name
                        incidentTime.text = incident.time.toString()
                        when (incident.goalType) {
                            Goal.TOUCHDOWN -> incidentIcon.setImageResource(R.drawable.ic_touchdown)
                            Goal.EXTRA_POINT -> incidentIcon.setImageResource(R.drawable.ic_extra_point)
                            Goal.FIELD_GOAL -> incidentIcon.setImageResource(R.drawable.ic_field_goal)
                            Goal.SAFETY -> incidentIcon.setImageResource(R.drawable.ic_safety)
                            else -> throw IllegalArgumentException()
                        }
                    }
                }

            }

        }
    }


    class BasketballViewHolder(private val binding: BasketballScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(incident: Incidents, sport: String) {
            binding.run {
                incidentTime.text = incident.time.toString()
                homeTeamScore.text = incident.homeScore.toString()
                awayTeamScore.text = incident.awayScore.toString()
                when (incident.goalType) {
                    Goal.ONE_POINT -> incidentIcon.setImageResource(R.drawable.ic_one_point)
                    Goal.TWO_POINTS -> incidentIcon.setImageResource(R.drawable.ic_two_points)
                    Goal.THREE_POINTS -> incidentIcon.setImageResource(R.drawable.ic_three_points)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_FOUL -> {
                incidents[position]?.let { (holder as FoulViewHolder).bind(it, sport) }
            }
            TYPE_SCORE -> {
                when (sport) {
                    Sport.FOOTBALL -> {
                        incidents[position]?.let { (holder as ScoreViewHolder).bind(it, sport) }
                    }
                    Sport.BASKETBALL -> {
                        incidents[position]?.let {
                            (holder as BasketballViewHolder).bind(
                                it,
                                sport
                            )
                        }
                    }
                    Sport.AMERICAN_FOOTBALL -> {
                        incidents[position]?.let { (holder as ScoreViewHolder).bind(it, sport) }
                    }
                    else -> throw IllegalArgumentException()
                }

            }
            TYPE_PERIOD -> {
                incidents[position]?.let { (holder as PeriodViewHolder).bind(it, sport) }
            }
            else -> throw IllegalArgumentException()
        }


        holder.itemView.setOnClickListener {
            val incident = incidents.getOrNull(position) as Incidents
            incident.let {
                PlayerDetailsActivity.start(holder.itemView.context, it.player.id)
            }
        }

        if (incidents[position]?.teamSide == "away")
            holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        else holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        if (incidents[position]?.scoringTeam == "away")
            holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        else holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_LTR


    }

    override fun getItemCount(): Int {
        return incidents.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(incidents: List<Incidents?>, sport: String) {
        this.incidents.clear()
        this.incidents.addAll(incidents)
        this.sport = sport
        notifyDataSetChanged()
    }


}
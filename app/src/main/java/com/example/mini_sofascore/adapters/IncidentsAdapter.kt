package com.example.mini_sofascore.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.R
import com.example.mini_sofascore.data.Incidents
import com.example.mini_sofascore.databinding.*


private const val TYPE_FOUL = 0
private const val TYPE_SCORE = 1
private const val TYPE_PERIOD = 2

class IncidentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var incidents: MutableList<Incidents?> = arrayListOf()
    private var sport: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOUL) {
            FoulViewHolder(
                FoulItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        } else if (viewType == TYPE_SCORE) {
            ScoreViewHolder(
                ScoreItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            PeriodViewHolder(
                PeriodItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }


    override fun getItemViewType(position: Int) = when (incidents[position]?.type) {
        "goal" -> TYPE_SCORE
        "card" -> TYPE_FOUL
        "period" -> TYPE_PERIOD
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
                    "Football" -> {
                        incidentTime.text = incident.time.toString()
                        incidentPlayerName.text = incident.player.name
                        incidentIcon.setImageResource(R.drawable.ic_icon_goal)
                    }
                    "Basketball" -> {
                        incidentPlayerName.text = incident.time.toString()
                        incidentPlayerName.gravity = Gravity.CENTER
                        when (incident.goalType) {
                            "onepoint" -> incidentIcon.setImageResource(R.drawable.ic_one_point)
                            "twopoint" -> incidentIcon.setImageResource(R.drawable.ic_two_points)
                            "threepoint" -> incidentIcon.setImageResource(R.drawable.ic_three_points)
                        }
                    }
                    "American football" -> {
                        incidentPlayerName.text = incident.player.name
                        incidentTime.text = incident.time.toString()
                        when (incident.goalType) {
                            "touchdown" -> incidentIcon.setImageResource(R.drawable.ic_touchdown)
                            "extrapoint" -> incidentIcon.setImageResource(R.drawable.ic_extra_point)
                            "fieldgoal" -> incidentIcon.setImageResource(R.drawable.ic_field_goal)
                            "safety" -> incidentIcon.setImageResource(R.drawable.ic_safety)
                        }
                    }
                }

            }

        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_FOUL) {
            incidents[position]?.let { (holder as FoulViewHolder).bind(it, sport) }
        } else if (getItemViewType(position) == TYPE_SCORE) {
            incidents[position]?.let { (holder as ScoreViewHolder).bind(it, sport) }
        } else
            incidents[position]?.let { (holder as PeriodViewHolder).bind(it, sport) }

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
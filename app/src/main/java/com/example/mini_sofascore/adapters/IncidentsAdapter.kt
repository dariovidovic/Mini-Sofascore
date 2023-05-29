package com.example.mini_sofascore.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_sofascore.R
import com.example.mini_sofascore.data.Incidents
import com.example.mini_sofascore.databinding.FoulItemBinding
import com.example.mini_sofascore.databinding.PeriodItemBinding
import com.example.mini_sofascore.databinding.ScoreItemBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.Sport


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
                    Sport.FOOTBALL -> {
                        incidentTime.text = incident.time.toString()
                        incidentPlayerName.text = incident.player.name
                        incidentIcon.setImageResource(R.drawable.ic_icon_goal)
                    }
                    Sport.BASKETBALL -> {
                        incidentPlayerName.visibility = View.GONE
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(binding.scoreConstraintLayout)
                        constraintSet.connect(
                            incidentTime.id,
                            ConstraintSet.START,
                            R.id.away_team_score,
                            ConstraintSet.START,
                            Helper.dpToPx(itemView.context,28)
                        )
                        constraintSet.connect(
                            incidentTime.id,
                            ConstraintSet.TOP,
                            R.id.score_constraint_layout,
                            ConstraintSet.TOP,
                            Helper.dpToPx(itemView.context,12)
                        )


                        constraintSet.applyTo(binding.scoreConstraintLayout)
                        incidentTime.text = incident.time.toString()

                        /*val iconParams = incidentIcon.layoutParams as ViewGroup.MarginLayoutParams
                        iconParams.topMargin = Helper.dpToPx(itemView.context,8)
                        incidentIcon.layoutParams = iconParams*/



                        when (incident.goalType) {
                            "onepoint" -> incidentIcon.setImageResource(R.drawable.ic_one_point)
                            "twopoint" -> incidentIcon.setImageResource(R.drawable.ic_two_points)
                            "threepoint" -> incidentIcon.setImageResource(R.drawable.ic_three_points)
                        }
                    }
                    Sport.AMERICAN_FOOTBALL -> {
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
package com.example.mini_sofascore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.data.Matches
import com.example.mini_sofascore.databinding.MatchesItemBinding

class EventsAdapter() : RecyclerView.Adapter<EventsAdapter.MatchesViewHolder>() {

    private var matches: MutableList<Matches?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder(
            MatchesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val match = matches[position]
        val context = holder.itemView.context

        holder.binding.homeTeamLogo.load("https://academy.dev.sofascore.com/team/${match?.homeTeam?.id}/image")
        holder.binding.awayTeamLogo.load("https://academy.dev.sofascore.com/team/${match?.awayTeam?.id}/image")
        if(match?.status == "notstarted"){
            holder.binding.matchStatus.text = ""
        }
        if(match?.homeScore.isNullOrEmpty()){
            holder.binding.homeTeamScore.text = ""
        }
        if(match?.homeScore.isNullOrEmpty()){
            holder.binding.awayTeamScore.text = ""
        }
        holder.binding.homeTeamName.text = match?.homeTeam?.name
        holder.binding.awayTeamName.text = match?.awayTeam?.name
        holder.binding.matchTime.text = match?.startDate?.subSequence(11,16)

    }

    override fun getItemCount(): Int {
        return matches.size
    }

    class MatchesViewHolder(val binding: MatchesItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(matches : MutableList<Matches?>){
        this.matches = matches
        notifyDataSetChanged()
    }
}
package com.example.mini_sofascore.utils

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.databinding.MatchesItemBinding

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
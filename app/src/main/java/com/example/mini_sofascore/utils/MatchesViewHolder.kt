package com.example.mini_sofascore.utils

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.R
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.databinding.MatchesItemBinding
import java.text.SimpleDateFormat

class MatchesViewHolder(private val binding: MatchesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(match: Match?) {
        binding.homeTeamLogo.load(Helper.getTeamImageUrl(match?.homeTeam?.id))
        binding.awayTeamLogo.load(Helper.getTeamImageUrl(match?.awayTeam?.id))


        val stringFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val hourFormat = SimpleDateFormat("HH:mm")
        val data = match?.startDate?.let { it -> stringFormat.parse(it) }
        when (match?.status) {
            Status.FINISHED -> binding.matchStatus.text = "FT"
            Status.NOT_STARTED -> binding.matchStatus.text = ""
        }

        binding.homeTeamScore.text = match?.homeScore?.total?.toString()
        binding.awayTeamScore.text = match?.awayScore?.total?.toString()

        when (match?.homeScore?.total?.compareTo(match.awayScore.total)) {
            -1 -> binding.awayTeamScore.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.on_surface_on_surface_lv_1
                )
            )
            1 -> binding.homeTeamScore.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.on_surface_on_surface_lv_1
                )
            )
        }

        binding.homeTeamName.text = match?.homeTeam?.name
        binding.awayTeamName.text = match?.awayTeam?.name
        binding.matchTime.text = data?.let { hourFormat.format(it) }
    }
}
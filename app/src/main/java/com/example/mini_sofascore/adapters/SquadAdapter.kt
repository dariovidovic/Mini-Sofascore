package com.example.mini_sofascore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mini_sofascore.R
import com.example.mini_sofascore.data.Player
import com.example.mini_sofascore.databinding.PlayerItemBinding
import com.example.mini_sofascore.utils.Helper
import java.util.*

class SquadAdapter : RecyclerView.Adapter<SquadAdapter.PlayerViewHolder>() {

    private var players: MutableList<Player?> = arrayListOf()

    class PlayerViewHolder(private var binding: PlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player?) {

           if(player?.country?.name == "Ivory Coast"){
               binding.countryLogo.setImageResource(R.drawable.ic_ivory_coast)
           }
            else{
               val playerCountryCode = Helper.getCountryCode(player?.country?.name ?: "")
               val countryImageUrl = Helper.getCountryImageUrl(playerCountryCode)
               binding.countryLogo.load(countryImageUrl)
           }

            binding.playerName.text = player?.name
            binding.countryName.text = player?.country?.name
            binding.playerLogo.load(Helper.getPlayerImageUrl(player?.id))



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            PlayerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun setData(players: List<Player?>) {
        this.players.clear()
        this.players.addAll(players)
        notifyDataSetChanged()

    }

}
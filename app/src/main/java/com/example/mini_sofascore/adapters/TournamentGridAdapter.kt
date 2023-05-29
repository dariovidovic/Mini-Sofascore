package com.example.mini_sofascore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.databinding.TournamentGridItemBinding
import com.example.mini_sofascore.utils.Helper

class TournamentGridAdapter(
    private val applicationContext: Context,
) : BaseAdapter() {

    private var tournaments: MutableList<Tournament?> = arrayListOf()

    override fun getCount(): Int {
        return tournaments.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = TournamentGridItemBinding.inflate(layoutInflater)

        binding.tournamentLogo.load(Helper.getTournamentImageUrl(tournaments[p0]?.id))
        binding.tournamentName.text = tournaments[p0]?.name

        return binding.root

    }

    fun setData(tournaments : List<Tournament?>){
        this.tournaments.clear()
        this.tournaments.addAll(tournaments)
        notifyDataSetChanged()
    }
}
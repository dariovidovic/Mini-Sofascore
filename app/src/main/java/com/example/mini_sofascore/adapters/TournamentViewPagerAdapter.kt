package com.example.mini_sofascore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mini_sofascore.ui.TournamentMatchesFragment
import com.example.mini_sofascore.ui.TournamentStandingsFragment

private const val NUM_TABS = 2


class TournamentViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val tournamentId : Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TournamentMatchesFragment.newInstance(tournamentId)
            1 -> return TournamentStandingsFragment.newInstance(tournamentId)
        }

        return TournamentMatchesFragment.newInstance(tournamentId)
    }
}
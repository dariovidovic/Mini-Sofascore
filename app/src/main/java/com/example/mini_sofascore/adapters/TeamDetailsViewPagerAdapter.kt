package com.example.mini_sofascore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mini_sofascore.ui.TeamDetailsFragment
import com.example.mini_sofascore.ui.TeamSquadFragment
import com.example.mini_sofascore.ui.TeamStandingFragment


private const val NUM_TABS = 4

class TeamDetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val teamId: Int,
    private val teamSport: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TeamDetailsFragment.newInstance(teamId)
            1 -> return TeamDetailsFragment.newInstance(teamId)
            2 -> return TeamStandingFragment.newInstance(teamId, teamSport)
        }
        return TeamSquadFragment.newInstance(teamId)
    }
}
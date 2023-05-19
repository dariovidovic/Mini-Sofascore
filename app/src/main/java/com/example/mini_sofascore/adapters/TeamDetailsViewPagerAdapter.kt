package com.example.mini_sofascore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mini_sofascore.ui.TeamDetailsFragment
import com.example.mini_sofascore.ui.TeamSquadFragment


private const val NUM_TABS = 4

class TeamDetailsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val teamId: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TeamDetailsFragment.newInstance(teamId)
            1 -> return TeamDetailsFragment.newInstance(teamId)
            2 -> return TeamDetailsFragment.newInstance(teamId)
        }
        return TeamSquadFragment.newInstance(teamId)
    }
}
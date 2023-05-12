package com.example.mini_sofascore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mini_sofascore.ui.LeaguesFragment


private const val NUM_TABS = 3

class LeaguesViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LeaguesFragment.newInstance("football")
            1 -> return LeaguesFragment.newInstance("basketball")
            2 -> return LeaguesFragment.newInstance("american-football")
        }
        return LeaguesFragment.newInstance("football")
    }
}
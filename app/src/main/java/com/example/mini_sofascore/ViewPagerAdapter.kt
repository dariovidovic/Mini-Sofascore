package com.example.mini_sofascore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mini_sofascore.ui.AmericanFootballFragment
import com.example.mini_sofascore.ui.BasketballFragment
import com.example.mini_sofascore.ui.FootballFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return FootballFragment.newInstance()
            1-> return BasketballFragment.newInstance()
            2-> return AmericanFootballFragment.newInstance()
        }
        return FootballFragment.newInstance()

    }
}
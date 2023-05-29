package com.example.mini_sofascore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.mini_sofascore.adapters.LeaguesViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityLeaguesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class LeaguesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaguesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaguesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val adapter = LeaguesViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
            tab.icon = ContextCompat.getDrawable(this, tabsIcons[position])
        }.attach()



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.run {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@LeaguesActivity,
                            R.color.surface_surface_1
                        )
                    )
                    tabLayout.setSelectedTabIndicatorHeight(4)

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

}

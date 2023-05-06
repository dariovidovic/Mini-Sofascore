package com.example.mini_sofascore

import android.graphics.Color
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mini_sofascore.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private lateinit var binding: ActivityMainBinding

val tabsArray = arrayOf("Football", "Basketball", "Am. Football")
val tabsIcons = intArrayOf(
    R.drawable.ic_icon_football, R.drawable.ic_icon_basketball, R.drawable.ic_icon_american_football
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
            tab.icon = resources.getDrawable(tabsIcons[position])

        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.run {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@MainActivity,
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
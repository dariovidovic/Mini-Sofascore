package com.example.mini_sofascore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.mini_sofascore.databinding.ActivityMainBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private lateinit var binding: ActivityMainBinding
private lateinit var matchesViewModel: MatchesViewModel


val tabsArray = arrayOf("Football", "Basketball", "Am. Football")
val tabsIcons = intArrayOf(
    R.drawable.ic_icon_football, R.drawable.ic_icon_basketball, R.drawable.ic_icon_american_football
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        matchesViewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.trophy -> {
                startActivity(Intent(this@MainActivity, LeaguesActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
package com.example.mini_sofascore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mini_sofascore.adapters.MatchesViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val tabsArray = arrayOf(
            resources.getString(R.string.football),
            resources.getString(R.string.basketball),
            resources.getString(R.string.am_football)
        )
        val tabsIcons = intArrayOf(
            R.drawable.ic_icon_football,
            R.drawable.ic_icon_basketball,
            R.drawable.ic_icon_american_football
        )

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val adapter = MatchesViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
            tab.icon = ContextCompat.getDrawable(this, tabsIcons[position])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.trophy -> {
                startActivity(Intent(this@MainActivity, LeaguesActivity::class.java))

            }
            R.id.settings -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))

            }
            R.id.fav_matches -> {
                startActivity(Intent(this@MainActivity, MyMatchesActivity::class.java))

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
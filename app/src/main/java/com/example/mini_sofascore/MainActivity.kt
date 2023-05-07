package com.example.mini_sofascore

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.databinding.ActivityMainBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private lateinit var binding: ActivityMainBinding
private lateinit var matchesViewModel : MatchesViewModel

val tabsArray = arrayOf("Football", "Basketball", "Am. Football")
val tabsIcons = intArrayOf(
    R.drawable.ic_icon_football, R.drawable.ic_icon_basketball, R.drawable.ic_icon_american_football
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        matchesViewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
            tab.icon =  ContextCompat.getDrawable(this, tabsIcons[position] )
        }.attach()


        binding.testButton.setOnClickListener {
            matchesViewModel.getMatchesByDate("football", "2023-05-03")
            Log.d("testiram", matchesViewModel.matches.value.toString())

        }

        matchesViewModel.getMatches().observe(this){
            Toast.makeText(this@MainActivity, "Doslo je do promjene", Toast.LENGTH_SHORT).show()
        }





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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
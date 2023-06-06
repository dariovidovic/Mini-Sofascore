package com.example.mini_sofascore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.mini_sofascore.adapters.LeaguesViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityLeaguesBinding
import com.google.android.material.tabs.TabLayoutMediator


class LeaguesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaguesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaguesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = LeaguesViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
            tab.icon = ContextCompat.getDrawable(this, tabsIcons[position])
        }.attach()

        binding.backButtonLayout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

}

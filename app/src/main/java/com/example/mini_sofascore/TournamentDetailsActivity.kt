package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mini_sofascore.adapters.TournamentViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityTournamentDetailsBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.viewmodels.TournamentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var tournamentViewModel: TournamentViewModel

class TournamentDetailsActivity : AppCompatActivity() {
    private val tabsArray = arrayOf("Matches", "Standings")
    private lateinit var binding: ActivityTournamentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tournamentViewModel = ViewModelProvider(this)[TournamentViewModel::class.java]

        val currentTournamentId = intent.extras?.getInt(TOURNAMENT_ID)

        tournamentViewModel.getTournamentById(currentTournamentId ?: 1)
        tournamentViewModel.tournament.observe(this) {
            binding.run {
                tournamentLogo.load(Helper.getTournamentImageUrl(currentTournamentId))
                tournamentName.text = tournamentViewModel.tournament.value?.name
                countryName.text = tournamentViewModel.tournament.value?.country?.name
            }
        }

        val adapter = TournamentViewPagerAdapter(supportFragmentManager, lifecycle, currentTournamentId?:1)
        binding.viewPager.adapter = adapter

        binding.backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.run {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@TournamentDetailsActivity,
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


    companion object {
        private const val TOURNAMENT_ID = "tournament_id"
        fun start(context: Context, id: Int) {
            Intent(context, TournamentDetailsActivity::class.java).apply {
                putExtra(TOURNAMENT_ID, id)
            }.also {
                context.startActivity(it)
            }

        }
    }
}
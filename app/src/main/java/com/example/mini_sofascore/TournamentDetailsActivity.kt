package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import com.example.mini_sofascore.adapters.TournamentViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityTournamentDetailsBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.TournamentViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TournamentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTournamentDetailsBinding
    private val tournamentViewModel by viewModels<TournamentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabsArray =
            arrayOf(resources.getString(R.string.matches), resources.getString(R.string.standings))

        val currentTournamentId = intent.extras?.getInt(Slug.TOURNAMENT_ID)

        tournamentViewModel.getTournamentById(currentTournamentId ?: 1)
        tournamentViewModel.tournament.observe(this) {
            val tournamentCountryCode = Helper.getCountryCode(it?.country?.name ?: "")
            binding.run {
                tournamentLogo.load(Helper.getTournamentImageUrl(currentTournamentId))
                tournamentName.text = tournamentViewModel.tournament.value?.name
                countryName.text = tournamentViewModel.tournament.value?.country?.name
                countryLogo.load(Helper.getCountryImageUrl(tournamentCountryCode))
            }
        }

        val adapter =
            TournamentViewPagerAdapter(supportFragmentManager, lifecycle, currentTournamentId ?: 1)
        binding.viewPager.adapter = adapter

        binding.backIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
        fun start(context: Context, id: Int) {
            Intent(context, TournamentDetailsActivity::class.java).apply {
                putExtra(Slug.TOURNAMENT_ID, id)
            }.also {
                context.startActivity(it)
            }

        }
    }
}
package com.example.mini_sofascore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mini_sofascore.adapters.TeamDetailsViewPagerAdapter
import com.example.mini_sofascore.databinding.ActivityTeamDetailsBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.viewmodels.TeamViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var viewModel: TeamViewModel

class TeamDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamDetailsBinding
    private val tabsArray = arrayOf("Details", "Matches", "Standings", "Squad")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[TeamViewModel::class.java]

        val currentTeamId = intent.extras?.getInt(TEAM_ID)
        val currentTeamSport = intent.extras?.getString(TEAM_SPORT)

        viewModel.getTeamDetails(currentTeamId ?: 1)
        viewModel.teamDetails.observe(this) {
            binding.run {
                teamLogo.load(Helper.getTeamImageUrl(currentTeamId))
                teamName.text = viewModel.teamDetails.value?.name
                countryName.text = viewModel.teamDetails.value?.country?.name
            }
        }

        val adapter =
            TeamDetailsViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                currentTeamId ?: 1,
                currentTeamSport ?: ""
            )
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.run {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@TeamDetailsActivity,
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
        private const val TEAM_ID = "teamId"
        private const val TEAM_SPORT = "teamSport"
        fun start(context: Context, teamId: Int, teamSport: String) {
            Intent(context, TeamDetailsActivity::class.java).apply {
                putExtra(TEAM_ID, teamId)
                putExtra(TEAM_SPORT, teamSport)
            }.also {
                context.startActivity(it)
            }
        }

    }
}
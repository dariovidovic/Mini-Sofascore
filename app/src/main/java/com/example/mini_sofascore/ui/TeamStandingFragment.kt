package com.example.mini_sofascore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mini_sofascore.R
import com.example.mini_sofascore.adapters.StandingsAdapter
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.databinding.FragmentTeamStandingBinding
import com.example.mini_sofascore.utils.Helper
import com.example.mini_sofascore.utils.Sport
import com.example.mini_sofascore.viewmodels.TeamViewModel
import com.example.mini_sofascore.viewmodels.TournamentViewModel
import java.lang.IllegalArgumentException


class TeamStandingFragment : Fragment() {

    private lateinit var binding: FragmentTeamStandingBinding
    private val teamViewModel by viewModels<TeamViewModel>()
    private val tournamentViewModel by viewModels<TournamentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val teamId = bundle?.getInt(TEAM_ID)
        teamViewModel.getTeamTournaments(teamId ?: 0)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = this.arguments
        val sportName = bundle?.getString(TEAM_SPORT)
        binding = FragmentTeamStandingBinding.inflate(inflater, container, false)

        teamViewModel.teamTournaments.observe(viewLifecycleOwner) {
            val spinnerAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            binding.teamLeaguesSpinner.adapter = spinnerAdapter
        }

        val standingsAdapter = StandingsAdapter()
        standingsAdapter.setSportName(sportName)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.teamStandingsRecyclerView.layoutManager = linearLayoutManager
        binding.teamStandingsRecyclerView.adapter = standingsAdapter

        binding.teamLeaguesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("InflateParams")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val tournament: Tournament = p0?.selectedItem as Tournament
                    binding.leagueLogo.load(Helper.getTournamentImageUrl(tournament.id))

                    tournamentViewModel.getTournamentStandingsById(tournament.id)

                    tournamentViewModel.tournamentStandings.observe(viewLifecycleOwner) {
                        val view: View
                        when (sportName) {
                            Sport.FOOTBALL -> {
                                view =
                                    layoutInflater.inflate(R.layout.football_standing_header, null)
                                binding.headerLayout.addView(view)
                                it[2]?.sortedStandingsRows?.let { standings ->
                                    standingsAdapter.setData(
                                        standings
                                    )
                                }
                            }
                            Sport.BASKETBALL -> {
                                view = layoutInflater.inflate(
                                    R.layout.basketball_standing_header,
                                    null
                                )
                                binding.headerLayout.addView(view)
                                it[0]?.sortedStandingsRows?.let { standings ->
                                    standingsAdapter.setData(
                                        standings
                                    )
                                }
                            }
                            Sport.AMERICAN_FOOTBALL -> {
                                view = layoutInflater.inflate(
                                    R.layout.american_football_standing_header,
                                    null
                                )
                                binding.headerLayout.addView(view)
                                it[0]?.sortedStandingsRows?.let { standings ->
                                    standingsAdapter.setData(
                                        standings
                                    )
                                }
                            }
                            else -> throw IllegalArgumentException()
                        }

                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        return binding.root
    }

    companion object {

        private const val TEAM_ID = "teamId"
        private const val TEAM_SPORT = "teamSport"

        fun newInstance(teamId: Int, teamSport: String): TeamStandingFragment {
            val fragment = TeamStandingFragment()
            val bundle = Bundle()
            bundle.putInt(TEAM_ID, teamId)
            bundle.putString(TEAM_SPORT, teamSport)
            fragment.arguments = bundle
            return fragment
        }

    }
}
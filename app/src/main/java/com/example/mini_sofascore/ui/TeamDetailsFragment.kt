package com.example.mini_sofascore.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.R
import com.example.mini_sofascore.adapters.EventsAdapter
import com.example.mini_sofascore.adapters.TournamentGridAdapter
import com.example.mini_sofascore.databinding.FragmentTeamDetailsBinding
import com.example.mini_sofascore.viewmodels.TeamViewModel


class TeamDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTeamDetailsBinding
    private val viewModel by viewModels<TeamViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val teamId = bundle?.getInt(TEAM_ID)
        viewModel.getTeamDetails(teamId ?: 0)
        viewModel.getTeamPlayers(teamId ?: 0)
        viewModel.getTeamTournaments(teamId ?: 0)
        viewModel.getTeamNextMatch(teamId ?: 0)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)
        var countryName = ""
        viewModel.teamDetails.observe(viewLifecycleOwner) {
            binding.coachName.text = resources.getString(R.string.coach_name, it?.managerName)
            binding.teamStadium.text = it?.venue
            countryName = it?.country?.name ?: ""
        }

        val gridAdapter = TournamentGridAdapter(requireContext())
        val eventsAdapter = EventsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.nextTeamMatchRecyclerView.layoutManager = linearLayoutManager
        binding.nextTeamMatchRecyclerView.adapter = eventsAdapter

        binding.teamTournamentsGrid.adapter = gridAdapter
        viewModel.teamTournaments.observe(viewLifecycleOwner) {
            gridAdapter.setData(it)
        }

        viewModel.teamNextMatches.observe(viewLifecycleOwner) {
            val nextMatchTournament = it[0]?.tournament?.name
            val nextMatch = it[0]
            val match = listOf(nextMatchTournament) + nextMatch
            eventsAdapter.setData(match)
        }

        viewModel.players.observe(viewLifecycleOwner) {
            binding.run {
                foreignPlayersChart.max = it.size
                foreignPlayersChart.progress =
                    it.count { player -> player?.country?.name != countryName }
                teamTotalPlayers.text = it.size.toString()
                teamForeignPlayers.text =
                    it.count { player -> player?.country?.name != countryName }.toString()
            }
        }

        return binding.root
    }

    companion object {

        private const val TEAM_ID = "teamId"

        fun newInstance(teamId: Int): TeamDetailsFragment {
            val fragment = TeamDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(TEAM_ID, teamId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
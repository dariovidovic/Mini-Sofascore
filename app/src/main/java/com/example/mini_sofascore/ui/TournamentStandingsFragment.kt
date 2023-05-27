package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.R
import com.example.mini_sofascore.adapters.StandingsAdapter
import com.example.mini_sofascore.databinding.FragmentTournamentStandingsBinding
import com.example.mini_sofascore.utils.Sport
import com.example.mini_sofascore.viewmodels.TournamentViewModel


class TournamentStandingsFragment : Fragment() {

    private var _binding: FragmentTournamentStandingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TournamentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val tournamentId = bundle?.getInt(TOURNAMENT_ID)
        viewModel.getTournamentStandingsById(tournamentId ?: 1)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTournamentStandingsBinding.inflate(layoutInflater, container, false)

        val adapter = StandingsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.standingsRecyclerView.layoutManager = linearLayoutManager
        binding.standingsRecyclerView.adapter = adapter

        viewModel.tournamentStandings.observe(viewLifecycleOwner) {
            val sportName = it[0]?.tournament?.sport?.name
            adapter.setSportName(sportName)
            val view: View

            when (sportName) {
                Sport.FOOTBALL -> {
                    view = layoutInflater.inflate(R.layout.football_standing_header, null)
                    binding.headerLayout.addView(view)
                    it[2]?.sortedStandingsRows?.let { standings-> adapter.setData(standings) }
                }
                Sport.BASKETBALL -> {
                    view = layoutInflater.inflate(R.layout.basketball_standing_header, null)
                    binding.headerLayout.addView(view)
                    it[0]?.sortedStandingsRows?.let { standings -> adapter.setData(standings) }
                }
                Sport.AMERICAN_FOOTBALL -> {
                    view = layoutInflater.inflate(R.layout.american_football_standing_header, null)
                    binding.headerLayout.addView(view)
                    it[0]?.sortedStandingsRows?.let { standings -> adapter.setData(standings) }
                }
                else -> throw IllegalArgumentException()

            }
        }

        return binding.root
    }

    companion object {

        const val TOURNAMENT_ID = "tournamentId"

        fun newInstance(currentTournamentId: Int): TournamentStandingsFragment {
            val fragment = TournamentStandingsFragment()
            val bundle = Bundle()
            bundle.putInt(TOURNAMENT_ID, currentTournamentId)
            fragment.arguments = bundle
            return fragment
        }

    }
}

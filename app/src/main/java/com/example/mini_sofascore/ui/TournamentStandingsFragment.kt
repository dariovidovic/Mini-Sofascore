package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.StandingsAdapter
import com.example.mini_sofascore.databinding.FragmentTournamentStandingsBinding
import com.example.mini_sofascore.viewmodels.TournamentViewModel

class TournamentStandingsFragment : Fragment() {

    private var _binding: FragmentTournamentStandingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TournamentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val tournamentId = bundle?.getInt("tournamentId")
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
            it[2]?.sortedStandingsRows?.let { it1 -> adapter.setData(it1) }
        }

        return binding.root
    }

    companion object {

        fun newInstance(currentTournamentId: Int): TournamentStandingsFragment {
            val fragment = TournamentStandingsFragment()
            val bundle = Bundle()
            bundle.putInt("tournamentId", currentTournamentId)
            fragment.arguments = bundle
            return fragment
        }

    }
}

package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.TournamentMatchesAdapter
import com.example.mini_sofascore.databinding.FragmentTournamentMatchesBinding
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.TournamentViewModel
import kotlinx.coroutines.launch

class TournamentMatchesFragment : Fragment() {

    private lateinit var binding: FragmentTournamentMatchesBinding
    private val viewModel by viewModels<TournamentViewModel>()
    private val adapter by lazy { TournamentMatchesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val tournamentId = bundle?.getInt(Slug.TOURNAMENT_ID)
        viewModel.tournamentId = tournamentId ?: 0
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentMatchesBinding.inflate(inflater, container, false)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.tournamentMatchesRecyclerView.adapter = adapter
        binding.tournamentMatchesRecyclerView.layoutManager = linearLayoutManager

        viewModel.tournamentMatches.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        return binding.root
    }

    companion object {

        fun newInstance(currentTournamentId: Int): TournamentMatchesFragment {
            val fragment = TournamentMatchesFragment()
            val bundle = Bundle()
            bundle.putInt(Slug.TOURNAMENT_ID, currentTournamentId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
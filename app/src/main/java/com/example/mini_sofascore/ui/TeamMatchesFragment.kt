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
import com.example.mini_sofascore.databinding.FragmentTeamMatchesBinding
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.MatchesViewModel
import kotlinx.coroutines.launch

class TeamMatchesFragment : Fragment() {

    private lateinit var binding: FragmentTeamMatchesBinding
    private val viewModel by viewModels<MatchesViewModel>()
    private val adapter by lazy { TournamentMatchesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val teamId = bundle?.getInt(Slug.TEAM_ID)
        viewModel.teamId = teamId ?: 0
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamMatchesBinding.inflate(inflater, container, false)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.teamMatchesRecyclerView.adapter = adapter
        binding.teamMatchesRecyclerView.layoutManager = linearLayoutManager

        viewModel.teamMatches.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }

        }

        return binding.root
    }

    companion object {
        fun newInstance(currentTeamId: Int): TeamMatchesFragment {
            val fragment = TeamMatchesFragment()
            val bundle = Bundle()
            bundle.putInt(Slug.TEAM_ID, currentTeamId)
            fragment.arguments = bundle
            return fragment
        }


    }
}
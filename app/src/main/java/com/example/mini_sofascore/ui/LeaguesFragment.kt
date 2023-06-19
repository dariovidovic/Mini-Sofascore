package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.EventsAdapter
import com.example.mini_sofascore.databinding.FragmentLeaguesBinding
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.MatchesViewModel

class LeaguesFragment : Fragment() {

    private lateinit var binding: FragmentLeaguesBinding
    private val viewModel by viewModels<MatchesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.LEAGUE)

        if (slug != null) {
            viewModel.getTournaments(slug)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaguesBinding.inflate(inflater, container, false)

        val eventsAdapter = EventsAdapter()
        val leaguesLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = leaguesLinearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter
        viewModel.tournaments.observe(viewLifecycleOwner) {
            eventsAdapter.setData(it)
        }
        return binding.root
    }

    companion object {
        fun newInstance(leagueSlug: String): LeaguesFragment {
            val fragment = LeaguesFragment()
            val bundle = Bundle()
            bundle.putString(Slug.LEAGUE, leagueSlug)
            fragment.arguments = bundle
            return fragment
        }

    }
}
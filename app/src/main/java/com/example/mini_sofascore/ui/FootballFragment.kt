package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.EventsAdapter
import com.example.mini_sofascore.databinding.FragmentFootballBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel


class FootballFragment : Fragment() {

    private lateinit var matchesViewModel : MatchesViewModel
    private var _binding: FragmentFootballBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFootballBinding.inflate(inflater, container, false)
        matchesViewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)

        val eventsAdapter = EventsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter


        binding.test.setOnClickListener {
            matchesViewModel.getMatchesByDate("football", "2023-05-03")
        }

        matchesViewModel.getMatches().observe(viewLifecycleOwner){
            eventsAdapter.setData(it.toMutableList())
        }

        return binding.root

    }

    companion object {
        fun newInstance() = FootballFragment()
    }
}
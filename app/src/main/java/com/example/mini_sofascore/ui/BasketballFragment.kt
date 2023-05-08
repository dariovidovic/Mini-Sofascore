package com.example.mini_sofascore.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.EventsAdapter
import com.example.mini_sofascore.R
import com.example.mini_sofascore.databinding.FragmentBasketballBinding
import com.example.mini_sofascore.databinding.FragmentFootballBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel

class BasketballFragment : Fragment() {
    private lateinit var matchesViewModel : MatchesViewModel
    private var _binding: FragmentBasketballBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBasketballBinding.inflate(inflater, container, false)
        matchesViewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)

        val eventsAdapter = EventsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter


        binding.test.setOnClickListener {
            matchesViewModel.getMatchesByDate("basketball", "2023-04-15")
        }

        matchesViewModel.getMatches().observe(viewLifecycleOwner){
            val sortedList = it.groupBy { it?.tournament?.name }.flatMap {
                listOf(it.key) + it.value
            }
            Log.d("sortedList", sortedList.toString())
            eventsAdapter.setData(sortedList)
        }

        return binding.root
    }

    companion object {
        fun newInstance() = BasketballFragment()

    }
}
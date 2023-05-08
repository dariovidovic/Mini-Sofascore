package com.example.mini_sofascore.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.EventsAdapter
import com.example.mini_sofascore.databinding.FragmentSportBinding
import com.example.mini_sofascore.viewmodels.MatchesViewModel

class SportFragment : Fragment() {
    private var _binding: FragmentSportBinding? = null
    private val binding get() = _binding!!
    private val matchesViewModel: MatchesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = this.arguments
        val slug = bundle?.getString("string")
        Toast.makeText(context, slug, Toast.LENGTH_SHORT).show()

        _binding = FragmentSportBinding.inflate(inflater, container, false)
        val eventsAdapter = EventsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter

        binding.testButton.setOnClickListener {
            if (slug != null) {
                matchesViewModel.getMatchesByDate(slug, "2023-04-15")
            }
        }

        matchesViewModel.matches.observe(viewLifecycleOwner){ it ->
            val sortedList = it.groupBy { it?.tournament?.name }.flatMap {
                listOf(it.key) + it.value
            }
            Log.d("sortedList", sortedList.toString())
            eventsAdapter.setData(sortedList)
        }

        return binding.root

    }

    companion object {

        fun newInstance(string: String) : SportFragment {
            val fragment = SportFragment()
            val bundle = Bundle()
            bundle.putString("string", string)
            fragment.arguments = bundle
            return fragment
        }
    }
}
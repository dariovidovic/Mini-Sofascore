package com.example.mini_sofascore.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.DatesAdapter
import com.example.mini_sofascore.adapters.EventsAdapter
import com.example.mini_sofascore.databinding.FragmentMatchesBinding
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.viewmodels.MatchesViewModel


class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MatchesViewModel>()

    private val datesList = listOf(
        "2023-04-15",
        "2023-04-16",
        "2023-04-17",
        "2023-04-18",
        "2023-04-19",
        "2023-04-20",
        "2023-04-21",
        "2023-04-22",
        "2023-04-23"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.SPORT)

        if (slug != null) {
            viewModel.getMatchesByDate(slug, "2023-04-15")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.SPORT)
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)

        val eventsAdapter = EventsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter

        val datesAdapter = DatesAdapter(datesList, DatesAdapter.OnClickListener {
            viewModel.getMatchesByDate(slug ?: "football", it ?: "2023-04-15")
        })
        val datesLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.datesRecyclerView.layoutManager = datesLinearLayoutManager
        binding.datesRecyclerView.adapter = datesAdapter

        viewModel.matches.observe(viewLifecycleOwner) { it ->
            val sortedList = it.groupBy { it?.tournament?.name }.flatMap {
                listOf(it.key) + it.value
            }
            eventsAdapter.setData(sortedList)
        }

        return binding.root

    }

    companion object {
        fun newInstance(sportSlug: String): MatchesFragment {
            val fragment = MatchesFragment()
            val bundle = Bundle()
            bundle.putString(Slug.SPORT, sportSlug)
            fragment.arguments = bundle
            return fragment
        }
    }
}

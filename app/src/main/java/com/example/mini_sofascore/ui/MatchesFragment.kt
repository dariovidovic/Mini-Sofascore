package com.example.mini_sofascore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.R
import com.example.mini_sofascore.adapters.DatesAdapter
import com.example.mini_sofascore.adapters.EventsAdapter
import com.example.mini_sofascore.databinding.FragmentMatchesBinding
import com.example.mini_sofascore.utils.Slug
import com.example.mini_sofascore.utils.Sport
import com.example.mini_sofascore.viewmodels.MatchesViewModel
import java.text.SimpleDateFormat

class MatchesFragment : Fragment() {

    private lateinit var binding: FragmentMatchesBinding
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
        "2023-04-23",
        "2023-04-24",
        "2023-04-25",
        "2023-04-26",
        "2023-04-27",
        "2023-04-28",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.SPORT)

        if (slug != null) {
            viewModel.getMatchesByDate(slug, "2023-04-15")
        }
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.SPORT)

        val eventsAdapter = EventsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter

        val stringFormat = SimpleDateFormat("yyyy-MM-dd")
        val dayFormat = SimpleDateFormat("E")
        var data = stringFormat.parse("2023-04-15")

        binding.matchesDate.text = data?.let { dayFormat.format(it) }

        val datesAdapter = DatesAdapter(datesList, DatesAdapter.OnClickListener { it ->
            viewModel.getMatchesByDate(slug ?: Sport.FOOTBALL, it ?: "2023-04-15")
            data = stringFormat.parse(it!!)
            binding.matchesDate.text = data?.let { dayFormat.format(it) }
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
            binding.numberOfEvents.text = resources.getString(R.string.matches_count, it.size)
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

package com.example.mini_sofascore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import java.util.*

class MatchesFragment : Fragment() {

    private lateinit var binding: FragmentMatchesBinding
    private val viewModel by viewModels<MatchesViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val slug = bundle?.getString(Slug.SPORT)
        val stringFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDay = stringFormat.format(Calendar.getInstance().time)

        if (slug != null) {
            viewModel.getMatchesByDate(slug, currentDay)
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

        val futureDates = mutableListOf<Date>()
        val pastDates = mutableListOf<Date>()

        var cal = Calendar.getInstance()
        var date = cal.time

        for (i in 1..70) {
            futureDates.add(date)
            cal.add(Calendar.DATE, 1)
            date = cal.time
        }
        cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        date = cal.time

        for (i in 1..70) {
            pastDates.add(date)
            cal.add(Calendar.DATE, -1)
            date = cal.time
        }

        val totalDates = mutableListOf<Date>()
        totalDates.addAll(pastDates.reversed())
        totalDates.addAll(futureDates)

        val stringFormat = SimpleDateFormat("yyyy-MM-dd")
        val dayFormat = SimpleDateFormat("E")
        var data = stringFormat.parse("2023-04-15")


        val eventsAdapter = EventsAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.eventsRecyclerView.layoutManager = linearLayoutManager
        binding.eventsRecyclerView.adapter = eventsAdapter

        binding.noMatchesToday.visibility = View.INVISIBLE
        binding.matchesDate.text =
            stringFormat.parse(stringFormat.format(Calendar.getInstance().time))
                ?.let { dayFormat.format(it) }

        val datesList = totalDates.map { date -> stringFormat.format(date) }

        val datesAdapter = DatesAdapter(datesList, DatesAdapter.OnClickListener { it ->
            viewModel.getMatchesByDate(slug ?: Sport.FOOTBALL, it ?: Slug.DATE)
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
            if (sortedList.isNotEmpty()) {
                binding.noMatchesToday.visibility = View.GONE
            } else
                binding.noMatchesToday.visibility = View.VISIBLE
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

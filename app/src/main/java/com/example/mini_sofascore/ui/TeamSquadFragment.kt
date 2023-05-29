package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_sofascore.adapters.SquadAdapter
import com.example.mini_sofascore.databinding.FragmentTeamSquadBinding
import com.example.mini_sofascore.viewmodels.TeamViewModel

class TeamSquadFragment : Fragment() {
    private lateinit var binding: FragmentTeamSquadBinding
    private val viewModel by viewModels<TeamViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val teamId = bundle?.getInt(TEAM_ID)
        viewModel.getTeamPlayers(teamId ?: 0)
        viewModel.getTeamDetails(teamId ?: 0)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamSquadBinding.inflate(inflater, container, false)

        val adapter = SquadAdapter()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.playersRecyclerView.adapter = adapter
        binding.playersRecyclerView.layoutManager = linearLayoutManager


        viewModel.teamDetails.observe(viewLifecycleOwner){
            binding.coachName.text = viewModel.teamDetails.value?.managerName
        }

        viewModel.players.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }


        return binding.root
    }

    companion object {

        private const val TEAM_ID = "teamId"

        fun newInstance(teamId: Int): TeamSquadFragment {
            val fragment = TeamSquadFragment()
            val bundle = Bundle()
            bundle.putInt(TEAM_ID, teamId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
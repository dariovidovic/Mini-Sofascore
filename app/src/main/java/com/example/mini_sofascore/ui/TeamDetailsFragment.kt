package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mini_sofascore.R



class TeamDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = this.arguments
        val teamId = bundle?.getInt(TEAM_ID)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_team_details, container, false)
    }

    companion object {

        private const val TEAM_ID = "teamId"

        fun newInstance(teamId : Int) : TeamDetailsFragment {
            val fragment = TeamDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(TEAM_ID, teamId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
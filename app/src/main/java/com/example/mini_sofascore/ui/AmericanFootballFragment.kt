package com.example.mini_sofascore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mini_sofascore.R

class AmericanFootballFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_american_football, container, false)
    }

    companion object {

        fun newInstance() = AmericanFootballFragment()
    }
}
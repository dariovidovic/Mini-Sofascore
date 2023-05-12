package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Matches
import com.example.mini_sofascore.data.Tournaments
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class MatchesViewModel : ViewModel() {

    private val _matches = MutableLiveData<List<Matches?>>()
    val matches: LiveData<List<Matches?>> = _matches
    private val _tournaments = MutableLiveData<List<Tournaments?>>()
    val tournaments: LiveData<List<Tournaments?>> = _tournaments


    fun getMatchesByDate(slug: String, date: String) {
        viewModelScope.launch {
            val matches = RetrofitHelper.getRetrofitInstance().getMatches(slug, date).body()
            matches?.also {
                _matches.postValue(it)
            }
        }
    }

    fun getTournaments(slug: String) {
        viewModelScope.launch {
            val tournaments = RetrofitHelper.getRetrofitInstance().getTournaments(slug).body()
            tournaments?.also {
                _tournaments.postValue(it)
            }
        }
    }

}
package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.StandingsResponse
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class TournamentViewModel : ViewModel() {

    private val _tournament = MutableLiveData<Tournament?>()
    val tournament : LiveData<Tournament?> = _tournament

    private val _tournamentStandings = MutableLiveData<List<StandingsResponse?>>()
    val tournamentStandings : LiveData<List<StandingsResponse?>> = _tournamentStandings

    fun getTournamentById(id: Int) {
        viewModelScope.launch {
            val tournament = RetrofitHelper.getRetrofitInstance().getTournamentById(id).body()
            tournament?.also {
                _tournament.postValue(tournament)
            }

        }
    }

    fun getTournamentStandingsById(id:Int) {
        viewModelScope.launch {
            val tournamentStandings = RetrofitHelper.getRetrofitInstance().getTournamentStandings(id).body()
            tournamentStandings?.also {
                _tournamentStandings.postValue(it)
            }
        }
    }

}
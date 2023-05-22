package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.data.Player
import com.example.mini_sofascore.data.Team
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val _players = MutableLiveData<List<Player?>>()
    val players: LiveData<List<Player?>> = _players

    private val _teamDetails = MutableLiveData<Team?>()
    val teamDetails : LiveData<Team?> = _teamDetails

    private val _teamTournaments = MutableLiveData<List<Tournament?>>()
    val teamTournaments : LiveData<List<Tournament?>> = _teamTournaments

    private val _teamNextMatches = MutableLiveData<List<Match?>>()
    val teamNextMatches : LiveData<List<Match?>> = _teamNextMatches

    fun getTeamPlayers(id: Int) {
        viewModelScope.launch {
            val players = RetrofitHelper.getRetrofitInstance().getTeamPlayers(id).body()
            players?.also {
                _players.postValue(it)
            }
        }
    }

    fun getTeamDetails(id: Int){
        viewModelScope.launch {
            val teamDetails = RetrofitHelper.getRetrofitInstance().getTeamDetails(id).body()
            teamDetails?.also {
                _teamDetails.postValue(it)
            }
        }
    }

    fun getTeamTournaments(id: Int){
        viewModelScope.launch {
            val teamTournaments = RetrofitHelper.getRetrofitInstance().getTeamTournaments(id).body()
            teamTournaments?.also {
                _teamTournaments.postValue(it)
            }
        }
    }

    fun getTeamNextMatch(id: Int){
        viewModelScope.launch {
            val teamNextMatches = RetrofitHelper.getRetrofitInstance().getTeamMatches(id, "next", 0).body()
            teamNextMatches?.also {
                _teamNextMatches.postValue(it)
            }
        }
    }

}
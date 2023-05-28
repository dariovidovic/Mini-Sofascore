package com.example.mini_sofascore.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.mini_sofascore.data.*
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val _players = MutableLiveData<List<Player?>>()
    val players: LiveData<List<Player?>> = _players

    private val _teamDetails = MutableLiveData<Team?>()
    val teamDetails: LiveData<Team?> = _teamDetails

    private val _teamTournaments = MutableLiveData<List<Tournament?>>()
    val teamTournaments: LiveData<List<Tournament?>> = _teamTournaments

    private val _teamInfo = MutableLiveData<TeamData?>()
    val teamInfo: LiveData<TeamData?> = _teamInfo

    var foreignPlayersCount: Int = 1
    var countryName: String = ""


    fun getTeamPlayers(id: Int) {
        viewModelScope.launch {
            val players = RetrofitHelper.getRetrofitInstance().getTeamPlayers(id).body()
            players?.also {
                _players.postValue(it)
            }
        }
    }

    fun getTeamDetails(id: Int) {
        viewModelScope.launch {
            val teamDetails = RetrofitHelper.getRetrofitInstance().getTeamDetails(id).body()
            teamDetails?.also {
                _teamDetails.postValue(it)
            }
        }
    }

    fun getTeamTournaments(id: Int) {
        viewModelScope.launch {
            val teamTournaments = RetrofitHelper.getRetrofitInstance().getTeamTournaments(id).body()
            teamTournaments?.also {
                _teamTournaments.postValue(it)
            }
        }
    }


    fun getTeamData(id: Int) {

        viewModelScope.launch {

            val teamPlayers = async {
                RetrofitHelper.getRetrofitInstance().getTeamPlayers(id).body()
            }

            val teamDetails = async {
                RetrofitHelper.getRetrofitInstance().getTeamDetails(id).body()
            }

            val teamTournaments = async {
                RetrofitHelper.getRetrofitInstance().getTeamTournaments(id).body()
            }

            val teamNextMatch = async {
                RetrofitHelper.getRetrofitInstance().getTeamMatches(id, "next", 0).body()
            }


            val players = teamPlayers.await()
            val details = teamDetails.await()
            val tournaments = teamTournaments.await()
            val nextMatch = teamNextMatch.await()

            countryName = details?.country?.name ?: "Unknown"

            foreignPlayersCount =
                players?.count { players -> players.country.name != countryName } ?: 0


            val teamData = TeamData(players, details, tournaments, nextMatch)
            _teamInfo.postValue(teamData)

        }

    }

}
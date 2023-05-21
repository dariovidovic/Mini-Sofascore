package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Player
import com.example.mini_sofascore.data.Team
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val _players = MutableLiveData<List<Player?>>()
    val players: LiveData<List<Player?>> = _players

    private val _teamDetails = MutableLiveData<Team?>()
    val teamDetails : LiveData<Team?> = _teamDetails

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

}
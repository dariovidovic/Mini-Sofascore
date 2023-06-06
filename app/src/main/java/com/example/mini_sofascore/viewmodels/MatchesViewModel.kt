package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.data.Tournament
import com.example.mini_sofascore.paging.MatchesPagingSource
import com.example.mini_sofascore.retrofit.RetrofitHelper
import com.example.mini_sofascore.utils.Type
import kotlinx.coroutines.launch

class MatchesViewModel : ViewModel() {

    private val _matches = MutableLiveData<List<Match?>>()
    val matches: LiveData<List<Match?>> = _matches

    private val _tournaments = MutableLiveData<List<Tournament?>>()
    val tournaments: LiveData<List<Tournament?>> = _tournaments

    private val _favMatches = MutableLiveData<List<Match?>>()
    val favMatches: LiveData<List<Match?>> = _favMatches

    private val tempList = arrayListOf<Match?>()

    var teamId: Int = 1

    val teamMatches = Pager(config = PagingConfig(
        pageSize = 10, enablePlaceholders = false
    ), 0, pagingSourceFactory = { MatchesPagingSource(teamId, Type.TEAM) }
    ).liveData


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

    fun getFavMatchById(id: Int){
        viewModelScope.launch {
            val match = RetrofitHelper.getRetrofitInstance().getFavMatch(id).body()
            tempList.add(match)
            _favMatches.postValue(tempList)
        }
    }

}
package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mini_sofascore.data.Player
import com.example.mini_sofascore.paging.MatchesPagingSource
import com.example.mini_sofascore.retrofit.RetrofitHelper
import com.example.mini_sofascore.utils.Type
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _playerDetails = MutableLiveData<Player>()
    val playerDetails: LiveData<Player> = _playerDetails

    var playerId: Int = 1

    val playerMatches = Pager(config = PagingConfig(
        pageSize = 10, enablePlaceholders = false
    ), 0, pagingSourceFactory = { MatchesPagingSource(playerId, Type.PLAYER) }
    ).liveData


    fun getPlayerDetails(id: Int){
        viewModelScope.launch {
            val playerDetails = RetrofitHelper.getRetrofitInstance().getPlayerDetails(id).body()
            playerDetails?.also {
                _playerDetails.postValue(it)
            }
        }
    }

}
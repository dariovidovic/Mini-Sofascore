package com.example.mini_sofascore.viewmodels

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Matches
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchesViewModel : ViewModel() {

    private val _matches = MutableLiveData<List<Matches?>>()
    val matches : LiveData<List<Matches?>> = _matches

    fun getMatches() : MutableLiveData<List<Matches?>> {
        return _matches
    }
    fun getMatchesByDate(slug: String, date: String){
        viewModelScope.launch(Dispatchers.IO) {
            val matches = RetrofitHelper.getRetrofitInstance().getMatches(slug,date).body()
            _matches.postValue(matches!!)
        }
    }

}
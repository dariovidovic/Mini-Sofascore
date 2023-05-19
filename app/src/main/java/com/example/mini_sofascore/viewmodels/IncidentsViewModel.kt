package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Incidents
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class IncidentsViewModel : ViewModel() {

    private val _incidents = MutableLiveData<List<Incidents?>>()
    val incidents: LiveData<List<Incidents?>> = _incidents

    fun getIncidentsById(id: Int) {
        viewModelScope.launch {
            val incidents = RetrofitHelper.getRetrofitInstance().getIncidents(id).body()
            incidents?.also {
                _incidents.postValue(it)
            }

        }
    }

}
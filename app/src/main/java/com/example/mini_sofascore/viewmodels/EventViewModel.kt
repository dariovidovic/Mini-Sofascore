package com.example.mini_sofascore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_sofascore.data.Event
import com.example.mini_sofascore.retrofit.RetrofitHelper
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    private val _event = MutableLiveData<Event?>()
    val event: LiveData<Event?> = _event

    fun getEventById(id: Int) {
        viewModelScope.launch {
            val event = RetrofitHelper.getRetrofitInstance().getEvent(id).body()
            event?.also {
                _event.postValue(event)
            }

        }
    }

}
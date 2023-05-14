package com.example.mini_sofascore.data

import java.io.Serializable

data class Tournament(val id: Int, val name: String, val sport: Sport, val country: Country) :
    Serializable

data class Sport(val name: String)

data class Country(val id: Int, val name: String)



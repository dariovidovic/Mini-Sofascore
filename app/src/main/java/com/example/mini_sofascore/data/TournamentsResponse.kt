package com.example.mini_sofascore.data

import java.io.Serializable

data class Tournaments(val id: Int, val name: String,val sport: Sport, val country: Country) : Serializable

data class Sport(val name: String)

data class Country(val name: String)



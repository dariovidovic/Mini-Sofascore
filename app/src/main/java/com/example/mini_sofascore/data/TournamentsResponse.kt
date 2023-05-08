package com.example.mini_sofascore.data

import java.io.Serializable

data class Tournaments(val id : Int, val name: String, val country: Country) : Serializable

data class Country(val name: String)

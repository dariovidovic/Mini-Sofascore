package com.example.mini_sofascore.data

import java.io.Serializable


data class Matches(
    val status: String,
    val startDate: String,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam
) : Serializable


data class HomeTeam(val name: String) : Serializable

data class AwayTeam(val name: String) : Serializable
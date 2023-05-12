package com.example.mini_sofascore.data

import java.io.Serializable


data class Matches(
    val tournament: Tournaments,
    val status: String,
    val startDate: String,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam,
    val homeScore: HomeScore,
    val awayScore: AwayScore
) : Serializable


data class HomeTeam(val id: Int, val name: String) : Serializable

data class AwayTeam(val id: Int, val name: String) : Serializable

data class HomeScore(val total: Int) : Serializable
data class AwayScore(val total: Int) : Serializable
package com.example.mini_sofascore.data

import java.io.Serializable


data class Match(
    val id: Int,
    val tournament: Tournament,
    val status: String,
    val startDate: String,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam,
    val homeScore: HomeScore,
    val awayScore: AwayScore,
    val round: Int
) : Serializable


data class HomeTeam(val id: Int, val name: String) : Serializable

data class AwayTeam(val id: Int, val name: String) : Serializable

data class HomeScore(val total: Int, val period1 : Int, val period2: Int) : Serializable

data class AwayScore(val total: Int, val period1 : Int, val period2: Int) : Serializable
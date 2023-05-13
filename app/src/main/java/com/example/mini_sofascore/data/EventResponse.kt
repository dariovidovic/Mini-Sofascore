package com.example.mini_sofascore.data

data class Event(
    val id: Int,
    val tournament: Tournaments,
    val status: String,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam,
    val homeScore: HomeScore,
    val awayScore: AwayScore,
    val round: Int
)

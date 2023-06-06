package com.example.mini_sofascore.data

data class Event(
    val id: Int,
    val slug: String,
    val tournament: Tournament,
    val status: String,
    val homeTeam: HomeTeam,
    val awayTeam: AwayTeam,
    val homeScore: HomeScore,
    val awayScore: AwayScore,
    val startDate: String,
    val round: Int
)

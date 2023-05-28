package com.example.mini_sofascore.data

data class TeamData(
    val players: List<Player>?,
    val details: Team?,
    val tournaments: List<Tournament>?,
    val nextMatches: List<Match>?
)

package com.cstcompany.data

import kotlinx.serialization.Serializable

@Serializable
data class GameDetails(
    val game: Game,
    val turn: Int,
    val board: Board,
    val you: Snake
)
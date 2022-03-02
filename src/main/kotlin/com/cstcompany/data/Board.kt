package com.cstcompany.data

import com.cstcompany.data.boardClasses.Food
import com.cstcompany.data.boardClasses.Hazard
import kotlinx.serialization.Serializable

@Serializable
data class Board(
    val food: List<Food>,
    val hazards: List<Hazard>,
    val height: Int,
    val width: Int,
    val snakes: List<Snake>
)
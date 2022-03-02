package com.cstcompany.data.boardClasses

import kotlinx.serialization.Serializable

@Serializable
data class Food(
    val x: Int,
    val y: Int
)
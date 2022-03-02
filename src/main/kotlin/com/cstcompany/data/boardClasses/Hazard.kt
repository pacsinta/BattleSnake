package com.cstcompany.data.boardClasses

import kotlinx.serialization.Serializable

@Serializable
data class Hazard(
    val x: Int,
    val y: Int
)
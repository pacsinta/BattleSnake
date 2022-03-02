package com.cstcompany.data.snakeClasses

import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val x: Int,
    val y: Int
)
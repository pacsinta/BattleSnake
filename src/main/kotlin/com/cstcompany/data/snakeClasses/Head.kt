package com.cstcompany.data.snakeClasses

import kotlinx.serialization.Serializable

@Serializable
data class Head(
    val x: Int,
    val y: Int
)
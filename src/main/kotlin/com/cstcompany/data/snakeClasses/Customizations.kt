package com.cstcompany.data.snakeClasses

import kotlinx.serialization.Serializable

@Serializable
data class Customizations(
    val color: String,
    val head: String,
    val tail: String
)
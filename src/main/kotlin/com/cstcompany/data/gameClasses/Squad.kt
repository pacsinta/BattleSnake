package com.cstcompany.data.gameClasses

import kotlinx.serialization.Serializable

@Serializable
data class Squad(
    val allowBodyCollisions: Boolean,
    val sharedElimination: Boolean,
    val sharedHealth: Boolean,
    val sharedLength: Boolean
)
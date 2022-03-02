package com.cstcompany.data

import com.cstcompany.data.gameClasses.Ruleset
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: String,
    val ruleset: Ruleset,
    val timeout: Int,
    val source: String
)

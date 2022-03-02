package com.cstcompany.data.gameClasses

import kotlinx.serialization.Serializable

@Serializable
data class Ruleset(
    val name: String,
    val version: String,
    val settings: RulesetSettings
)

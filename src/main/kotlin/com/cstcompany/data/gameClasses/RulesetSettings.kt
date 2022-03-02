package com.cstcompany.data.gameClasses

import kotlinx.serialization.Serializable

@Serializable
data class RulesetSettings(
    val foodSpawnChance: Int,
    val hazardDamagePerTurn: Int,
    val map: String,
    val minimumFood: Int,
    val squad: Squad,
    val royale: Royale
)
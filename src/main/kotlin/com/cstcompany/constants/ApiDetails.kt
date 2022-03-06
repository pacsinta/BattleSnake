package com.cstcompany.constants

import kotlinx.serialization.Serializable

@Serializable
data class ApiDetails(
    val apiVersion: Int = 1,
    val author: String = "Patrik Csikós",
    val color: String = "#888888",
    val head: String = "replit-mark",
    val tail: String = "rbc-necktie",
    val version: String = "0.0.1-beta"
)
package com.cstcompany.data

import com.cstcompany.data.snakeClasses.Body
import com.cstcompany.data.snakeClasses.Customizations
import com.cstcompany.data.snakeClasses.Head
import kotlinx.serialization.Serializable

@Serializable
data class Snake(
    val body: List<Body>,
    val customizations: Customizations,
    val head: Head,
    val health: Int,
    val id: String,
    val latency: String,
    val length: Int,
    val name: String,
    val shout: String,
    val squad: String
)
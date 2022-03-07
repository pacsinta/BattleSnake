package com.cstcompany.plugins

import io.ktor.shared.serialization.kotlinx.gson.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
        }
    }
}

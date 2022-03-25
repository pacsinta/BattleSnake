package com.cstcompany

import com.cstcompany.constants.ApiDetails
import com.cstcompany.data.GameDetails
import com.cstcompany.data.Move
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Application.configureRouting() {
    routing {
        get {
            call.respond(ApiDetails())
        }

        post("/start") {
            val gameDetails: GameDetails = call.receive()
            call.response.status(HttpStatusCode.OK)
        }

        post("/move") {
            val gameDetails: GameDetails = call.receive()
            val move = BasicStrategy().move(gameDetails, application)

            call.respond(move)
        }

        post("/end") {
            val gameDetails: GameDetails = call.receive()
        }
    }
}


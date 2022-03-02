package com.cstcompany

import com.cstcompany.constants.ApiDetails
import com.cstcompany.data.GameDetails
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    routing {
        get{
            call.respond(ApiDetails())
        }

        post("/start"){
            val gameDetails: GameDetails = call.receive()
            call.response.status(HttpStatusCode.OK)
        }

        post("/move"){
            val gameDetails: GameDetails = call.receive()

            val possibleMoves = arrayOf(true, true, true, true)  // up, down, left, right

            //Protect my snake to move back on its own neck
            val neck = gameDetails.you.body[0]
            val head = gameDetails.you.head

            if(neck.x < head.x){
                possibleMoves[2] = false  //left
            }else if(neck.x > head.x){
                possibleMoves[3] = false  //right
            }else if(neck.y < head.y){
                possibleMoves[1] = false  //down
            }else if(neck.y > head.y){
                possibleMoves[0] = false  //up
            }


            //Protect my snake of hitting wall
            when(head.x){
                gameDetails.board.width -> possibleMoves[3] = false
                0 -> possibleMoves[2] = false
            }
            when(head.y){
                gameDetails.board.height -> possibleMoves[0] = false
                0 -> possibleMoves[1] = false
            }


            //Protect my snake of hitting other snakes


            //Find food


            //Make move
        }

        post("/end"){
            val gameDetails: GameDetails = call.receive()
        }
    }
}


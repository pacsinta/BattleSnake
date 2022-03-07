package com.cstcompany

import com.cstcompany.constants.ApiDetails
import com.cstcompany.data.GameDetails
import com.cstcompany.data.Move
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun calcDistanceSquare(
    x1: Int,
    y1: Int,
    x2: Int,
    y2: Int
): Int {
    val xDelta = x1 - x2
    val yDelta = y1 - y2

    return xDelta * xDelta + yDelta * yDelta
}

fun getRandomMove(
    possibleMoves: Array<Boolean>
): Int{
    val indexArray = arrayOf(0, 1, 2, 3)
    indexArray.shuffle()

    for(i in indexArray){
        if(possibleMoves[i]){
            return i
        }
    }

    return indexArray[0]
}

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

            val possibleMoves = arrayOf(true, true, true, true)  // up, down, left, right

            //Protect my snake to move back on its own neck
            val neck = gameDetails.you.body[0]
            val head = gameDetails.you.head

            if (neck.x < head.x) {
                possibleMoves[2] = false  //left
            } else if (neck.x > head.x) {
                possibleMoves[3] = false  //right
            } else if (neck.y < head.y) {
                possibleMoves[1] = false  //down
            } else if (neck.y > head.y) {
                possibleMoves[0] = false  //up
            }


            //Protect my snake of hitting wall
            when (head.x) {
                gameDetails.board.width -> possibleMoves[3] = false
                0 -> possibleMoves[2] = false
            }
            when (head.y) {
                gameDetails.board.height -> possibleMoves[0] = false
                0 -> possibleMoves[1] = false
            }


            val move = goToTheNearestFood(
                possibleMoves = possibleMoves,
                gameDetails = gameDetails
            )
            call.respond(move)
        }

        post("/end") {
            val gameDetails: GameDetails = call.receive()
        }
    }
}

fun goToTheNearestFood(
    possibleMoves: Array<Boolean>,
    gameDetails: GameDetails
): Move {
    val x = gameDetails.you.head.x
    val y = gameDetails.you.head.y

    //Find the nearest food

    var minDistance = calcDistanceSquare(
        x,
        y,
        gameDetails.board.food[0].x,
        gameDetails.board.food[0].y
    )
    var foodId = 0

    for ((i, food) in gameDetails.board.food.withIndex()) {
        val distance = calcDistanceSquare(
            x,
            y,
            food.x,
            food.y
        )

        if (distance < minDistance) {
            minDistance = distance
            foodId = i
        }
    }

    //Get the next move to the food
    val foodX = gameDetails.board.food[foodId].x
    val foodY = gameDetails.board.food[foodId].y

    if (x < foodX){
        possibleMoves[2] = false //left
    }else if(x > foodX){
        possibleMoves[3] = false //right
    }else{
        possibleMoves[2] = false
        possibleMoves[3] = false
    }

    if (y < foodY){
        possibleMoves[1] = false //left
    }else if(y > foodY){
        possibleMoves[0] = false //right
    }else{
        possibleMoves[1] = false
        possibleMoves[0] = false
    }

    val move = when(getRandomMove(possibleMoves)){
        0 -> Move(move = "up")
        1 -> Move(move = "down")
        2 -> Move(move = "left")
        else -> Move(move = "right")
    }

    return move
}
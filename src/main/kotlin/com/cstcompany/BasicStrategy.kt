package com.cstcompany

import com.cstcompany.data.GameDetails
import com.cstcompany.data.Move
import io.ktor.server.application.*


const val MAX_DEPTH = 2

class BasicStrategy {
    private val possibleMoves =
        Array(4) { MAX_DEPTH } // index from 0 -> 4: up, down, left, right  | value: 0 = the least optimal move

    // Calculate the square of the distance between two points
    private fun calcDistanceSquare(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ): Int {
        val xDelta = x1 - x2
        val yDelta = y1 - y2
        return xDelta * xDelta + yDelta * yDelta
    }

    private fun getRandomMove(): Int {
        val indexArray = arrayOf(0, 1, 2, 3)
        indexArray.shuffle()

        val max = possibleMoves.maxOrNull() ?: MAX_DEPTH

        for (i in indexArray) {
            if (possibleMoves[i] == max) {
                return i
            }
        }
        return indexArray[0]
    }

    private fun goToTheNearestFood(
        gameDetails: GameDetails
    ) {
        if (gameDetails.board.food.isEmpty()) {
            return
        }

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

        if (x < foodX) {
            possibleMoves[2] = 1 //left
        } else if (x > foodX) {
            possibleMoves[3] = 1 //right
        } else {
            possibleMoves[2] = 1
            possibleMoves[3] = 1
        }

        if (y < foodY) {
            possibleMoves[1] = 1 //left
        } else if (y > foodY) {
            possibleMoves[0] = 1 //right
        } else {
            possibleMoves[1] = 1
            possibleMoves[0] = 1
        }
    }


    // Function to make the next move
    fun move(
        gameDetails: GameDetails,
        application: Application
    ): Move {
        val head = gameDetails.you.head

        goToTheNearestFood(gameDetails = gameDetails)

        //Protect my snake of hitting wall
        when (head.x) {
            gameDetails.board.width - 1 -> possibleMoves[3] = 0
            0 -> possibleMoves[2] = 0
        }
        when (head.y) {
            gameDetails.board.height - 1 -> possibleMoves[0] = 0
            0 -> possibleMoves[1] = 0
        }

        //protect my snake hitting a snake (including own snake)
        for (snake in gameDetails.board.snakes) {
            for (body in snake.body) {
                if (body.x == head.x + 1 && body.y == head.y) {
                    possibleMoves[3] = 0
                } else if (body.x == head.x - 1 && body.y == head.y) {
                    possibleMoves[2] = 0
                }
                if (body.y == head.y + 1 && body.x == head.x) {
                    possibleMoves[0] = 0
                } else if (body.y == head.y - 1 && body.x == head.x) {
                    possibleMoves[1] = 0
                }
            }
        }



        // Select a move and convert it to a direction
        val move = when (getRandomMove()) {
            0 -> Move(move = "up")
            1 -> Move(move = "down")
            2 -> Move(move = "left")
            else -> Move(move = "right")
        }

        // Log the move
        var logString = ""
        logString += "move: ${move.move}    "
        logString += "up: ${possibleMoves[0]}, down: ${possibleMoves[1]}, left: ${possibleMoves[2]}, right: ${possibleMoves[3]}"

        application.log.info(logString)

        return move
    }

    private fun calcSpace(
        gameDetails: GameDetails,
    ){

    }
}
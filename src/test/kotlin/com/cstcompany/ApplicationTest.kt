package com.cstcompany

import com.cstcompany.plugins.configureSerialization
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
            configureSerialization()
        }

        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testStart() = testApplication {
        application {
            configureRouting()
            configureSerialization()
        }

        val response = client.post("/start")
        assertEquals(HttpStatusCode.OK, response.status)
    }


    val testCall = "{\"game\":{\"id\":\"4d930f7b-ad5b-4479-9c2e-b65f0c2b5195\",\"ruleset\":{\"name\":\"solo\",\"version\":\"v1.1.3\",\"settings\":{\"foodSpawnChance\":15,\"minimumFood\":1,\"hazardDamagePerTurn\":0,\"hazardMap\":\"\",\"hazardMapAuthor\":\"\",\"royale\":{\"shrinkEveryNTurns\":0},\"squad\":{\"allowBodyCollisions\":false,\"sharedElimination\":false,\"sharedHealth\":false,\"sharedLength\":false}}},\"map\":\"standard\",\"timeout\":500,\"source\":\"challenge\"},\"turn\":0,\"board\":{\"height\":7,\"width\":7,\"snakes\":[{\"id\":\"gs_t86FKR8whYPVYvkkpkKSvfWV\",\"name\":\"Pacsinta\",\"latency\":\"\",\"health\":100,\"body\":[{\"x\":1,\"y\":5},{\"x\":1,\"y\":5},{\"x\":1,\"y\":5}],\"head\":{\"x\":1,\"y\":5},\"length\":3,\"shout\":\"\",\"squad\":\"\",\"customizations\":{\"color\":\"#888888\",\"head\":\"replit-mark\",\"tail\":\"rbc-necktie\"}}],\"food\":[{\"x\":0,\"y\":4},{\"x\":3,\"y\":3}],\"hazards\":[]},\"you\":{\"id\":\"gs_t86FKR8whYPVYvkkpkKSvfWV\",\"name\":\"Pacsinta\",\"latency\":\"\",\"health\":100,\"body\":[{\"x\":1,\"y\":5},{\"x\":1,\"y\":5},{\"x\":1,\"y\":5}],\"head\":{\"x\":1,\"y\":5},\"length\":3,\"shout\":\"\",\"squad\":\"\",\"customizations\":{\"color\":\"#888888\",\"head\":\"replit-mark\",\"tail\":\"rbc-necktie\"}}}\n"

    @Test
    fun testMove() = testApplication {
        application {
            configureRouting()
            configureSerialization()
        }

        val response = client.post("/move"){
            header("Content-Type", "application/json")
            setBody(testCall)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}

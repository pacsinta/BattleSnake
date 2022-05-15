package com.cstcompany

import com.cstcompany.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore


const val enableHTTPS = true
const val portHTTP = 7000
const val portHTTPS = 7001


lateinit var environment: ApplicationEngineEnvironment
fun main() {
    if (enableHTTPS) {
        val ks = KeyStore.getInstance("JKS")
        val pwd = File("pwd")

        val passwords = pwd.readText().split(';')
        val keyPassword = passwords[0]
        val jksPassword = passwords[1]
        val alias = passwords[2]

        FileInputStream("keystore.jks").use { file ->
            ks.load(file, keyPassword.toCharArray())
        }

        environment = applicationEngineEnvironment {
            module(Application::configureRouting)
            module(Application::configureSerialization)
            /*module{
                Application.configureRouting
                Application.configureRouting
            }*/

            connector {
                port = portHTTP
            }

            sslConnector(
                keyStore = ks,
                keyAlias = alias,
                keyStorePassword = { jksPassword.toCharArray() },
                privateKeyPassword = { keyPassword.toCharArray() },
            ) {
                port = portHTTPS
            }
        }
    } else {
        environment = applicationEngineEnvironment {
            module(Application::configureRouting)
            module(Application::configureSerialization)
            connector {
                port = portHTTP
            }
        }
    }




    embeddedServer(Netty, environment = environment).start(wait = true)
}
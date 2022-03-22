package com.cstcompany

import com.cstcompany.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore


fun main() {
    val ks = KeyStore.getInstance("JKS")


    val pwd = File("pwd")

    val passwords = pwd.readText().split(';')
    val keyPassword = passwords[0]
    val jksPassword = passwords[1]
    val alias = passwords[2]

    FileInputStream("keystore.jks").use { file->
        ks.load(file, keyPassword.toCharArray())
    }


    val environment = applicationEngineEnvironment {
        module(Application::configureRouting)
        module(Application::configureSerialization)
        /*module{
            Application.configureRouting
            Application.configureRouting
        }*/

        connector {
            port=7000
        }

        sslConnector(
            keyStore = ks,
            keyAlias = alias,
            keyStorePassword = {jksPassword.toCharArray()},
            privateKeyPassword = {keyPassword.toCharArray()},
        ){
            port = 7001
        }
    }

    embeddedServer(Netty, environment = environment).start(wait = true)
}
package com.cstcompany

import com.cstcompany.plugins.*
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
        module {
            configureRouting()
            configureSerialization()
        }

        sslConnector(
            keyStore = ks,
            keyAlias = alias,
            keyStorePassword = {keyPassword.toCharArray()},
            privateKeyPassword = {jksPassword.toCharArray()},
        ){
            port = 4000
        }
    }

    embeddedServer(Netty, environment = environment).start(wait = true)
}
package com.cstcompany

import alias
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.cstcompany.plugins.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.network.tls.certificates.*
import io.ktor.network.tls.extensions.*
import jksPassword
import keyPassword
import java.io.File

fun main() {
    val keyStoreFile = File("~/keystore.jks")
    val keystore = generateCertificate(
        file = keyStoreFile,
        keyAlias = alias,
        keyPassword = keyPassword,
        jksPassword = jksPassword
    )

    val environment = applicationEngineEnvironment {
        module {
            configureRouting()
            configureSerialization()
        }

        sslConnector(
            keyStore = keystore,
            keyAlias = alias,
            keyStorePassword = {keyPassword.toCharArray()},
            privateKeyPassword = {jksPassword.toCharArray()},
        ){
            port = 4000
            keyStorePath = keyStoreFile
        }
    }

    embeddedServer(Netty, environment = environment) {

    }.start(wait = true)
}
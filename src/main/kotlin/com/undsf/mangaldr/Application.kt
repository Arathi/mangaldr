package com.undsf.mangaldr

import com.undsf.mangaldr.plugins.configureRouting
import com.undsf.mangaldr.plugins.configureWebSocket
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    // configureSecurity()
    // configureSerialization()
    configureWebSocket()
    configureRouting()
}

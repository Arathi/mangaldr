package com.undsf.mangaldr

import com.undsf.mangaldr.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    // val server = embeddedServer(
    //     Netty,
    //     port = 30314,
    //     host = "0.0.0.0",
    //     module = Application::module
    // )
    // server.start(wait = true)
    EngineMain.main(args)
}

fun Application.module() {
    // configureSecurity()
    // configureSerialization()
    configureRouting()
}

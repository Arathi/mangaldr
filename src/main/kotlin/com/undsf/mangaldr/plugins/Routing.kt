package com.undsf.mangaldr.plugins

import com.undsf.mangaldr.messages.BasicResponse
import com.undsf.mangaldr.messages.DataResponse
import com.undsf.mangaldr.models.TaskGroup
import io.ktor.serialization.gson.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.slf4j.LoggerFactory

typealias Aria2Client = com.undsf.aria2.Client

private val logger = LoggerFactory.getLogger("Routing")

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Manga LoaDeR, an Aria2 Task Manager.")
        }
    }
}

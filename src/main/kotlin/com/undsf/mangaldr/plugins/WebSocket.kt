package com.undsf.mangaldr.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration

fun Application.configureWebSocket() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        webSocket("/connect") {
            send("""please input `/start` to start session.""")
            for (frame in incoming) {
                if (frame !is io.ktor.websocket.Frame.Text) {
                    continue
                }

                val input = frame.readText().trim()
                when {
                    input == "/start" -> {
                        send("started.")
                    }
                    input == "/end" -> {
                        send("terminated.")
                        close(
                            io.ktor.websocket.CloseReason(
                                io.ktor.websocket.CloseReason.Codes.NORMAL, "Bye."
                            )
                        )
                    }
                    input.startsWith("/") -> {
                        send("no command $input")
                    }
                    else -> {
                        send("reply: $input")
                    }
                }
            }
        }
    }
}
package com.undsf.mangaldr.plugins

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
    install(ContentNegotiation) {
        gson()
    }

    routing {
        val prefix = "/api/aria2"
        val aria2Client = Aria2Client(
            "http://127.0.0.1:6800/jsonrpc",
            "47bfbcf3"
        )

        get("/") {
            call.respondText("Aria2 Download Task Manager")
        }

        get("$prefix/getVersion") {
            val version = aria2Client.getVersion()
            call.respond(version!!)
        }

        post("$prefix/addUris") {
            val group: TaskGroup = call.receive()
            for (task in group.tasks) {
                val gid = aria2Client.addUri(
                    listOf(task.uri),
                    group.dir,
                    task.fileName,
                    group.proxy
                )
                logger.info("下载任务创建成功，gid为$gid")
            }
        }

        webSocket("/connect") {
            send("""please input `/start` to start session.""")
            for (frame in incoming) {
                if (frame !is Frame.Text) {
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
                            CloseReason(
                                CloseReason.Codes.NORMAL, "Bye."
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

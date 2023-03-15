package com.undsf.mangaldr

import com.undsf.mangaldr.controllers.Aria2Controller
import com.undsf.mangaldr.controllers.RestController
import com.undsf.mangaldr.plugins.Aria2Client
import com.undsf.mangaldr.plugins.configureRouting
import com.undsf.mangaldr.plugins.configureSerialization
import com.undsf.mangaldr.plugins.configureWebSocket
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.kodein.di.*
import org.kodein.type.jvmType

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        port = 30314
    ) {
        kodeinApplication { application ->
            application.install(DefaultHeaders)
            application.install(ContentNegotiation) { gson() }

            bindSingleton {
                val baseUrl = "http://127.0.0.1:6800/jsonrpc"
                val token = "47bfbcf3"
                log.info("创建Aria2客户端，baseUrl=$baseUrl，token=$token")
                Aria2Client(baseUrl, token)
            }

            bindSingleton {
                Aria2Controller(this.di)
            }
        }
    }.start(wait = true)
}

fun Application.kodeinApplication(
    kodeinMapper: DI.MainBuilder.(Application) -> Unit = {}
) {
    val application = this

    val kodein = DI {
        bind<Application>() with instance(application)
        kodeinMapper(this, application)
    }

    routing {
        for (bind in kodein.container.tree.bindings) {
            val clazz = bind.key.type.jvmType as? Class<*>?
            if (clazz != null && RestController::class.java.isAssignableFrom(clazz)) {
                val res by kodein.Instance(bind.key.type)
                application.log.info("Registering $res routes.")
                (res as RestController).apply {
                    registerRoutes()
                }
            }
        }
    }
}

fun Application.module() {
    // configureSecurity()
    configureSerialization()
    configureWebSocket()
    configureRouting()
}

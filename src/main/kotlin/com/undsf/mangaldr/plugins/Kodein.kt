package com.undsf.mangaldr.plugins

import com.undsf.mangaldr.controllers.Aria2Controller
import io.ktor.server.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import org.slf4j.LoggerFactory

// fun Application.configureKodein() {
//     DI {
//         bind<Aria2Client>() with singleton {
//             val baseUrl = environment.config.config("aria2.baseUrl").toString()
//             val token = environment.config.config("aria2.token").toString()
//             log.info("创建Aria2客户端，baseUrl=$baseUrl，token=$token")
//             Aria2Client(baseUrl, token)
//         }
//     }
// }
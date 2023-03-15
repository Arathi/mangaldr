package com.undsf.mangaldr.controllers

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.DIAware
import org.kodein.di.instance

abstract class RestController(
    open val requestMapping: String
) : DIAware {
    /**
     * Application实例
     */
    open val application: Application by instance()

    /**
     * 注册路由
     */
    abstract fun Routing.registerRoutes()
}
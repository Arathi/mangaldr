package com.undsf.mangaldr.controllers

import com.undsf.mangaldr.messages.BasicResponse
import com.undsf.mangaldr.messages.DataResponse
import com.undsf.mangaldr.models.TaskGroup
import com.undsf.mangaldr.plugins.Aria2Client
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

class Aria2Controller(override val di: DI) : RestController("/api/aria2") {
    private val client: Aria2Client by instance()
    private val log get() = application.log

    suspend fun getVersion() : DataResponse<*> {
        val version = client.getVersion()
        log.info("aria2版本号：$version")
        return DataResponse(0, "版本号获取成功", version)
    }

    suspend fun addUris(group: TaskGroup) : DataResponse<*> {
        val results = mutableMapOf<String, String>()
        for (task in group.tasks) {
            val gid = client.addUri(
                listOf(task.uri),
                group.dir,
                task.fileName,
                group.proxy
            )
            if (gid != null) {
                results[gid] = task.uri
                log.info("$gid=${task.uri}")
            }
            else {
                log.warn("下载任务 ${task.uri} => ${task.fileName} 创建失败")
            }
        }
        log.info("${results.size}个任务创建成功")
        return DataResponse(0, "下载任务组创建成功", results)
    }

    suspend fun getTaskStatus(gid: String?) : DataResponse<*> {
        if (gid == null) {
            return BasicResponse(1, "未获取到gid")
        }
        val status = client.tellStatus(gid)
        return DataResponse(0, "任务状态获取成功", status)
    }

    override fun Routing.registerRoutes() {
        get("$requestMapping/getVersion") {
            val resp = getVersion()
            call.respond(resp)
        }

        post("$requestMapping/addUris") {
            val group: TaskGroup = call.receive()
            val resp = addUris(group)
            call.respond(resp)
        }

        get("$requestMapping/task/{gid}") {
            val gid = call.parameters["gid"]
            val resp = getTaskStatus(gid)
            call.respond(resp)
        }
    }
}
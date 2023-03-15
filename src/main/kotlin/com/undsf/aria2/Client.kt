package com.undsf.aria2

import com.google.gson.reflect.TypeToken
import com.undsf.aria2.messages.*
import com.undsf.jsonrpc.messages.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

typealias JsonRpcClient = com.undsf.jsonrpc.Client

private val logger = LoggerFactory.getLogger(Client::class.java)

class Client(
    var baseUrl: String = "http://127.0.0.1:6800/jsonrpc",
    var token: String? = null
) : JsonRpcClient() {
    private val secret: String? get() =
        if (token != null) "token:$token"
        else null

    suspend fun <R> jsonrpc(method: String, params: ParamList? = null, respType: TypeToken<R>) : R {
        val respJson = jsonrpcAsText(baseUrl, method, params)
        return gson.fromJson(respJson, respType)
    }

    suspend fun addUri(
        uris: List<String>,
        dir: String? = null,
        fileName: String? = null,
        proxy: String? = null,
        position: UInt? = null
    ): String? {
        val method = "aria2.addUri"
        val options = OptionsBuilder(
            dir = dir,
            fileName = fileName,
            proxy = proxy
        ).build()
        val resp: Response<String, Void> = jsonrpc(
            method,
            ParamList.of(secret, uris, options, position),
            stringResultResponseType
        )
        return resp.result
    }

    suspend fun remove(gid: String) : String? {
        val method = "aria2.remove"
        val resp = jsonrpc(
            method,
            ParamList.of(secret, gid),
            stringResultResponseType
        )
        return resp.result
    }

    suspend fun tellStatus(gid: String) : Status? {
        val method = "aria2.tellStatus"
        val resp = jsonrpc(
            method,
            ParamList.of(secret, gid, fieldNames),
            statusResultResponseType
        )
        return resp.result
    }

    suspend fun tellTasks() {

    }

    suspend fun getVersion() : String {
        val method = "aria2.getVersion"
        val resp: Response<GetVersionResult, Void> = jsonrpc(
            method,
            ParamList(secret),
            getVersionResultRequestType
        )
        return resp.result?.version!!
    }

    companion object {
        val getVersionResultRequestType = object : TypeToken<Response<GetVersionResult, Void>>(){}
        val stringResultResponseType = object : TypeToken<Response<String, Void>>(){}
        val statusResultResponseType = object : TypeToken<Response<Status, Void>>(){}

        val fieldNames = listOf(
            "gid",  // aria2任务ID
            "status", // 状态码
            "totalLength", // 总长度
            "completedLength" // 已下载长度
        )
    }
}
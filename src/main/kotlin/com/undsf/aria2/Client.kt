package com.undsf.aria2

import com.google.gson.reflect.TypeToken
import com.undsf.aria2.messages.Aria2Request
import com.undsf.aria2.messages.GetVersionResult
import com.undsf.aria2.messages.ParamList
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

typealias JsonRpcClient = com.undsf.jsonrpc.Client

private val logger = LoggerFactory.getLogger(Client::class.java)

class Client(
    var baseUrl: String = "http://127.0.0.1:6800/jsonrpc",
    var token: String? = null
) : JsonRpcClient() {
    private val secret: String? get() =
        if (token != null) "token:$token"
        else null

    suspend fun addUri(
        uris: List<String>,
        dir: String? = null,
        fileName: String? = null,
        proxy: String? = null,
        position: UInt? = null
    ): String? {
        val method = "aria2.addUri"

        val options = mutableMapOf<String, String>()
        if (dir != null) {
            options["dir"] = dir
        }
        if (fileName != null) {
            options["out"] = fileName
        }
        if (proxy != null) {
            options["all-proxy"] = proxy
        }

        val params = ParamList.of(
            secret,
            uris,
            options,
            position
        )

        val respType = object : TypeToken<Response<String, Void>>(){}
        val respJson = jsonrpcAsText(baseUrl, method, params)
        val resp: Response<String, Void> = gson.fromJson(respJson, respType)
        return resp.result
    }

    suspend fun getVersion() : String? {
        val method = "aria2.getVersion"
        logger.info("获取aria2版本信息（$method）")
        val params = ParamList(secret)

        val respType = object : TypeToken<Response<GetVersionResult, Void>>(){}
        val respJson = jsonrpcAsText(baseUrl, method, params)
        val resp: Response<GetVersionResult, Void> = gson.fromJson(respJson, respType)
        return resp.result?.version
    }
}
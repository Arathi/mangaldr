package com.undsf.jsonrpc

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.undsf.jsonrpc.messages.Request
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

private val logger = LoggerFactory.getLogger(Client::class.java)

open class Client {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    val gson = Gson()

    suspend fun <P, R, ED> jsonrpc(url: String, method: String, params: P?) : Response<R, ED> {
        val respJson = jsonrpcAsText(url, method, params)
        val respType = object : TypeToken<Response<R, ED>>() {}
        val resp: Response<R, ED> = gson.fromJson(respJson, respType)

        return resp
    }

    suspend fun jsonrpcAsText(url: String, method: String, params: Any?) : String {
        val req: Request<Any> = Request(
            method = method,
            params = params
        )
        val reqJson = gson.toJson(req)

        logger.info("执行JSON-RPC，地址：$url，方法：$method")
        logger.info("请求报文：$reqJson")
        val respJson: String = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(req)
        }.bodyAsText()
        logger.info("响应报文：$respJson")

        return respJson
    }
}
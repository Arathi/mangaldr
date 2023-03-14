package com.undsf.jsonrpc.messages

import java.util.*

open class Request<P>(
    /**
     * 协议版本
     */
    var jsonrpc: String = VERSION_2_0,

    /**
     * 方法
     */
    var method: String,

    /**
     * 参数
     */
    var params: P? = null,

    /**
     * ID
     */
    var id: String = UUID.randomUUID().toString()
) {
    companion object {
        const val VERSION_2_0 = "2.0"
    }
}
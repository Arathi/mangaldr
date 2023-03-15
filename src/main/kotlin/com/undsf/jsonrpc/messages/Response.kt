package com.undsf.jsonrpc.messages

class Response<R,ED>(
    /**
     * 协议版本
     */
    var jsonrpc: String = Request.VERSION_2_0,

    /**
     * 结果
     */
    var result: R? = null,

    /**
     * 错误
     */
    var error: Error<ED>? = null,

    /**
     * ID，与请求的一致
     */
    var id: Any
) {
    constructor(result: R, id: String) : this(
        jsonrpc = Request.VERSION_2_0,
        result = result,
        error = null,
        id = id
    )

    constructor(error: Error<ED>, id: String) : this(
        jsonrpc = Request.VERSION_2_0,
        result = null,
        error = error,
        id = id
    )

    constructor(errorCode: Int, id: String) : this(
        jsonrpc = Request.VERSION_2_0,
        result = null,
        error = Error<ED>(errorCode),
        id = id
    )
}
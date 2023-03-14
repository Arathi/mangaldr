package com.undsf.jsonrpc.messages

class Error<D>(
    var code: Int = 0,
    var message: String? = null,
    var data: D? = null
) {
    init {
        if (message == null) {
            message = Messages.getOrDefault(code, "Unknown error")
        }
    }

    companion object {
        const val CodeParseError = -32700
        const val CodeInvalidRequest = -32600
        const val CodeMethodNotFound = -32601
        const val CodeInvalidParams = -32602
        const val CodeInternalError = -32603

        val Messages = mutableMapOf(
            CodeParseError to "Parse error",
            CodeInvalidRequest to "Invalid Request",
            CodeMethodNotFound to "Method not found",
            CodeInvalidParams to "Invalid params",
            CodeInternalError to "Internal error",
        )
    }
}
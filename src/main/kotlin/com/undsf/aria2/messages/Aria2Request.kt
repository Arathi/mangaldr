package com.undsf.aria2.messages

import com.undsf.jsonrpc.messages.Request

class Aria2Request(
    method: String,
    paramList: ParamList? = null
) : Request<ParamList>(
    method = method,
    params = paramList
)
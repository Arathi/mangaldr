package com.undsf.mangaldr.messages

class BasicResponse(
    code: Int = 0,
    message: String? = null
) : DataResponse<Void>(
    code,
    message,
    null
)
package com.undsf.mangaldr.messages

open class DataResponse<D>(
    var code: Int = 0,
    var message: String? = null,
    var data: D? = null
)
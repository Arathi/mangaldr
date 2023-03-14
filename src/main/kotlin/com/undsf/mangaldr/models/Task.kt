package com.undsf.mangaldr.models

import kotlinx.serialization.Serializable

// @Serializable
data class Task(
    /**
     * 任务ID
     */
    var id: Long?,

    /**
     * URI
     */
    var uri: String,

    /**
     * 文件名
     */
    var fileName: String? = null,

    /**
     * aria2任务标识
     */
    var gid: String? = null
) {
}
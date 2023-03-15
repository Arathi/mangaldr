package com.undsf.mangaldr.models

data class TaskGroup(
    /**
     * 任务组ID
     */
    var id: Long?,

    /**
     * 名称
     */
    var name: String?,

    /**
     * 目录
     */
    var dir: String?,

    /**
     * 代理服务器
     */
    var proxy: String?,
) {
    /**
     * 显示名称
     */
    val displayName: String get() {
        if (name != null) return name!!
        if (id != null) return "任务组$id"
        return "未命名任务组"
    }

    /**
     * 任务列表
     */
    var tasks: MutableList<Task> = mutableListOf()

    /**
     * 添加任务
     */
    fun addTask(uri: String, fileName: String? = null) {
        tasks.add(
            Task(
                id = null,
                uri = uri,
                fileName = fileName
            )
        )
    }
}
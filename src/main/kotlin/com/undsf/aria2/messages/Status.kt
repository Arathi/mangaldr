package com.undsf.aria2.messages

data class Status(
    var gid: String? = null,
    var status: String? = null,
    var totalLength: String? = null,
    var completedLength: String? = null
) {
    companion object {
        const val StatusActive = "active"
        const val StatusWaiting = "waiting"
        const val StatusPaused = "paused"
        const val StatusError = "error"
        const val StatusComplete = "complete"
        const val StatusRemoved = "removed"
    }
}
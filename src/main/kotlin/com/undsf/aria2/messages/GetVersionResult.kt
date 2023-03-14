package com.undsf.aria2.messages

data class GetVersionResult(
    var enabledFeatures: MutableList<String> = mutableListOf(),
    var version: String
)
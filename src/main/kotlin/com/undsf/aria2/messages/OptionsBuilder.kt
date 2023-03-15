package com.undsf.aria2.messages

class OptionsBuilder(
    val dir: String? = null, // --dir
    val fileName: String? = null, // --out
    val proxy: String? = null, // --all-proxy
    val timeout: Int? = null, // --timeout
) {
    fun build() : Map<String, Any> {
        val options = mutableMapOf<String, Any>()

        if (dir != null) options["dir"] = dir
        if (fileName != null) options["out"] = fileName
        if (proxy != null) options["all-proxy"] = proxy
        if (timeout != null) options["timeout"] = timeout

        return options
    }
}
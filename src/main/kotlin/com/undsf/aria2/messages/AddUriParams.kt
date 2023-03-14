package com.undsf.aria2.messages

data class AddUriParams(
    var secret: String?,
    var uris: List<String>,
    var options: MutableMap<String, Any>? = null,
    var position: UInt? = null
) {
    fun toParamList() : ParamList {
        val paramList = ParamList(secret)
        paramList.add(uris)
        if (options != null && options!!.isNotEmpty()) {
            paramList.add(options!!)
        }
        if (position != null) {
            paramList.add(position!!)
        }
        return paramList
    }
}
package com.undsf.aria2.messages

class ParamList(secret: String?) : ArrayList<Any>() {
    init {
        if (secret != null) {
            add(secret)
        }
    }

    companion object {
        fun of(vararg args: Any?) : ParamList {
            val params = ParamList(null)
            for (arg in args) {
                if (arg != null) {
                    params.add(arg)
                }
            }
            return params
        }
    }
}
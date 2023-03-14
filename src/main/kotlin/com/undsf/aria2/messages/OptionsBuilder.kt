package com.undsf.aria2.messages

data class OptionsBuilder(
    val dir: String?, // --dir
    val fileName: String?, // --out
    val proxy: String?, // --all-proxy
    val timeout: Int?,

) {
}
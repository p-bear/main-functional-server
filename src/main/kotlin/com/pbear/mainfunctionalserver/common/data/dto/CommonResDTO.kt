package com.pbear.mainfunctionalserver.common.data.dto

data class CommonResDTO(
    val result: String = "success",
    var data: Any?
) {
    constructor(data: Any?) : this("success", data)
}
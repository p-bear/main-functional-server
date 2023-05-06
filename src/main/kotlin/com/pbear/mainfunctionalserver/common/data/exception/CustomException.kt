package com.pbear.mainfunctionalserver.common.data.exception

import org.springframework.web.server.ResponseStatusException

open class CustomException(
    val responseErrorCode: ResponseErrorCode, cause: Throwable?, var messageArgumentMap: Map<String, String>? = null
): ResponseStatusException(responseErrorCode.httpStatus, responseErrorCode.message, cause)
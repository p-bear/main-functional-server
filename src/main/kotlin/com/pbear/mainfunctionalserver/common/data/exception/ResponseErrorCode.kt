package com.pbear.mainfunctionalserver.common.data.exception

import org.springframework.http.HttpStatus

enum class ResponseErrorCode(
    val code: String,
    val httpStatus: HttpStatus,
    val message: String
) {
    COMMON_1("common.1", HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Error"),

    ACCOUNT_1("account.1", HttpStatus.BAD_REQUEST, "Duplicated Id, userId={userId}"),
    ACCOUNT_2("account.2", HttpStatus.BAD_REQUEST, "id notFound, userId={userId}")
}
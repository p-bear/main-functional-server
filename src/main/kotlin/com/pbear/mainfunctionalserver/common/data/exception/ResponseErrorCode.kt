package com.pbear.mainfunctionalserver.common.data.exception

import org.springframework.http.HttpStatus

enum class ResponseErrorCode(
    val code: String,
    val httpStatus: HttpStatus,
    val message: String
) {
    COMMON_1("common.1", HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Error"),
    COMMON_2("common.2", HttpStatus.BAD_REQUEST, "notfound by id"),

    ACCOUNT_1("account.1", HttpStatus.BAD_REQUEST, "Duplicated Id, userId={userId}"),
    ACCOUNT_2("account.2", HttpStatus.BAD_REQUEST, "id notFound, userId={userId}"),
    ACCOUNT_3("account.3", HttpStatus.UNAUTHORIZED, "password not match, userId={userId}"),

    EASY_CALENDAR_1("easyCalendar.1", HttpStatus.UNAUTHORIZED, "google AccessToken not valid")
}
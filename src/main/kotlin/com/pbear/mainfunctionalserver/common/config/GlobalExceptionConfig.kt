package com.pbear.mainfunctionalserver.common.config

import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import org.springframework.boot.autoconfigure.web.WebProperties.Resources
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.support.DefaultServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalExceptionConfig(
    errorAttributes: DefaultErrorAttributes,
    val resources: Resources,
    applicationContext: AbstractApplicationContext
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {

    private final val defaultServerCodecConfigurer = DefaultServerCodecConfigurer()

    init {
        super.setMessageWriters(this.defaultServerCodecConfigurer.writers)
        super.setMessageReaders(this.defaultServerCodecConfigurer.readers)

    }
    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> = RouterFunctions
        .route(RequestPredicates.all(), this::createErrorResponse)

    fun createErrorResponse(serverRequest: ServerRequest): Mono<ServerResponse> = ServerResponse
        .status(this.createErrorResponseHttpStatus(getError(serverRequest)))
        .bodyValue(this.createErrorResponseBody(getError(serverRequest), serverRequest.path()))

    fun createErrorResponseHttpStatus(throwable: Throwable): HttpStatus =
        when (throwable) {
            is CustomException -> throwable.responseErrorCode.httpStatus
            is ResponseStatusException -> throwable.status
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

    fun createErrorResponseBody(throwable: Throwable, path: String) =
        when (throwable) {
            is CustomException -> this.createErrorResponseBody(throwable.responseErrorCode, throwable.messageArgumentMap, path)
            is ResponseStatusException -> this.createErrorResponseBody("common.${throwable.rawStatusCode}", throwable.message, path)
            else -> this.createErrorResponseBody(ResponseErrorCode.COMMON_1, null, path)
        }

    fun createErrorResponseBody(responseErrorCode: ResponseErrorCode, messageArgumentMap: Map<String, String>?, path: String) =
        this.createErrorResponseBody(
            responseErrorCode.code,
            this.createMessage(responseErrorCode.message, messageArgumentMap),
            path)

    fun createErrorResponseBody(code: String, message: String, path: String) =
        mapOf(
            "code" to code,
            "message" to message,
            "path" to path)

    fun createMessage(message: String, messageArgumentMap: Map<String, String>?) =
        when (messageArgumentMap) {
            null -> message
            else -> {
                var result:String = message
                messageArgumentMap.forEach {
                    result = result.replace(it.key, it.value)
                }
                result
            }
        }

}
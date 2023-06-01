package com.pbear.mainfunctionalserver.main.easycalendar

import com.fasterxml.jackson.databind.ObjectMapper
import com.pbear.mainfunctionalserver.common.data.dto.CommonResDTO
import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Component
class EasyCalendarTemplateHandler(
    private val objectMapper: ObjectMapper,
    private val calendarTemplateRepository: CalendarTemplateRepository
) {
    fun handleGetTemplateType(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().bodyValue(CommonResDTO(mapOf("type" to CalendarTemplateType.values())))
    }

    fun handlePostTemplate(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(ReqPostCalendarTemplate::class.java)
            // TODO: modelMapper 같은걸로 class to class 매핑 작업
            .map { CalendarTemplate(
                accountId = serverRequest.headers().firstHeader("accountId")?.toLong() ?: throw CustomException(ResponseErrorCode.ACCOUNT_1),
                title = it.title,
                summary = it.summary,
                type = it.type,
                properties = it.properties?.run { objectMapper.writeValueAsString(this) })
            }
            .flatMap { this.calendarTemplateRepository.save(it) }
            .map { ResCalendarTemplate(it.id!!, it.title, it.summary, it.type, it.properties?.run { objectMapper.readValue(this, HashMap::class.java) as Map<String, Any> }) }
            .flatMap { ServerResponse.ok().bodyValue(CommonResDTO(it)) }
    }

    fun handleGetTemplateList(serverRequest: ServerRequest): Mono<ServerResponse> {
        return this.calendarTemplateRepository.findAllByAccountId(
            serverRequest.headers().firstHeader("accountId")?.toLong() ?: throw CustomException(ResponseErrorCode.ACCOUNT_1))
            .map { ResCalendarTemplate(it.id!!, it.title, it.summary, it.type, it.properties?.run { objectMapper.readValue(this, HashMap::class.java) as Map<String, Any> }) }
            .collect(Collectors.toList())
            .flatMap { ServerResponse.ok().bodyValue(CommonResDTO(it)) }
    }
}
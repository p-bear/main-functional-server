package com.pbear.mainfunctionalserver.main.easycalendar

import com.fasterxml.jackson.databind.ObjectMapper
import com.pbear.mainfunctionalserver.common.data.dto.CommonResDTO
import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class EasyCalendarTemplateHandler(
    private val objectMapper: ObjectMapper,
    private val calendarTemplateRepository: CalendarTemplateRepository
) {

    fun handlePostTemplate(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(ReqPostCalendarTemplate::class.java)
            // TODO: modelMapper 같은걸로 class to class 매핑 작업
            .map { CalendarTemplate(
                accountId = serverRequest.headers().firstHeader("accountId")?.toLong() ?: throw CustomException(ResponseErrorCode.ACCOUNT_1),
                title = it.title,
                summary = it.summary,
                properties = it.properties?.run { objectMapper.writeValueAsString(this) })
            }
            .flatMap { this.calendarTemplateRepository.save(it) }
            .map { ResCalendarTemplate(it.id!!, it.title, it.summary, it.properties?.run { objectMapper.readValue(this, HashMap::class.java) as Map<String, Any> }) }
            .flatMap { ServerResponse.ok().bodyValue(CommonResDTO(it)) }
    }

    fun handleGetTemplateList(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().build()
    }
}
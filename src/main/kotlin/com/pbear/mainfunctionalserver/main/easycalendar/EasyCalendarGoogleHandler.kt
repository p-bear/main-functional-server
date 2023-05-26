package com.pbear.mainfunctionalserver.main.easycalendar

import com.pbear.mainfunctionalserver.common.data.dto.CommonResDTO
import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class EasyCalendarGoogleHandler(private val googleCalendarWebClient: GoogleCalendarWebClient) {
    fun handleGetCalendarList(serverRequest: ServerRequest): Mono<ServerResponse> {
        return this.googleCalendarWebClient.getWebClient()
            .get()
            .uri{
                it.path(CALENDAR_LIST_PATH).build()
            }
            .headers { this.setGoogleAccessToken(serverRequest, it) }
            .retrieve()
            .bodyToMono(HashMap::class.java)
            .flatMap { ServerResponse.ok().bodyValue(CommonResDTO(it)) }
    }

    fun handleGetCalendarsEvents(serverRequest: ServerRequest): Mono<ServerResponse> {
        return this.googleCalendarWebClient.getWebClient()
            .get()
            .uri{
                it.path(CALENDAR_EVENTS_PATH.replace("{calendarId}", serverRequest.pathVariable("calendarId")))
                if (serverRequest.queryParam("maxResults").isPresent) {
                    it.queryParam("maxResults", serverRequest.queryParam("maxResults").get())
                }
                if (serverRequest.queryParam("timeMin").isPresent) {
                    it.queryParam("timeMin", serverRequest.queryParam("timeMin").get())
                } else {
                    it.queryParam("timeMin", LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC).toString())
                }
                it.build()
            }
            .headers { this.setGoogleAccessToken(serverRequest, it) }
            .retrieve()
            .bodyToMono(HashMap::class.java)
            .flatMap { ServerResponse.ok().bodyValue(CommonResDTO(it)) }
    }



    private fun setGoogleAccessToken(serverRequest: ServerRequest, headers: HttpHeaders) {
        headers.setBearerAuth(serverRequest.headers().firstHeader("googleAccessToken")
                ?: throw CustomException(ResponseErrorCode.EASY_CALENDAR_1, null, null))
    }

    companion object {
        private const val CALENDAR_LIST_PATH = "/users/me/calendarList"
        private const val CALENDAR_EVENTS_PATH = "/calendars/{calendarId}/events"
    }
}
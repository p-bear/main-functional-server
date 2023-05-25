package com.pbear.mainfunctionalserver.main.easycalendar

import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Component
class GoogleCalendarWebClient(private val webClient: WebClient) {
    @Value("\${webclient.url.google.calendar}")
    private val googleCalendarUrl: String = ""

    private lateinit var googleCalendarWebClient: WebClient;

    @PostConstruct
    fun initWebClient() {
        this.googleCalendarWebClient = this.webClient.mutate()
            .baseUrl(this.googleCalendarUrl)
            .filter(ExchangeFilterFunction.ofResponseProcessor {
                when (it.statusCode()) {
                    HttpStatus.UNAUTHORIZED -> Mono.error(CustomException(ResponseErrorCode.EASY_CALENDAR_1, null, null))
                    else -> Mono.just(it)
                }
            })
            .build()
    }

    fun getWebClient() = this.googleCalendarWebClient
}
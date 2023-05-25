package com.pbear.mainfunctionalserver.main.easycalendar

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class EasyCalendarRouter {
    @Bean
    fun routeEasyCalendar(easyCalendarHandler: EasyCalendarHandler): RouterFunction<ServerResponse>  = RouterFunctions
        .nest(
            RequestPredicates.path("/api/easyCalendar"),
            router {
                GET("/calendarList", easyCalendarHandler::handleGetCalendarList)
                GET("/calendars/{calendarId}/events", easyCalendarHandler::handleGetCalendarsEvents)
            })
}
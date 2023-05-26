package com.pbear.mainfunctionalserver.main.easycalendar

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class EasyCalendarRouter {
    @Bean
    fun routeEasyCalendar(
        easyCalendarGoogleHandler: EasyCalendarGoogleHandler,
        easyCalendarTemplateHandler: EasyCalendarTemplateHandler): RouterFunction<ServerResponse>  = RouterFunctions
        .nest(
            RequestPredicates.path("/api/easyCalendar"),
            router {
                GET("/calendarList", easyCalendarGoogleHandler::handleGetCalendarList)
                GET("/calendars/{calendarId}/events", easyCalendarGoogleHandler::handleGetCalendarsEvents)

                GET("/template/type", easyCalendarTemplateHandler::handleGetTemplateType)
                POST("/template", easyCalendarTemplateHandler::handlePostTemplate)
                GET("/template", easyCalendarTemplateHandler::handleGetTemplateList)
            })
}
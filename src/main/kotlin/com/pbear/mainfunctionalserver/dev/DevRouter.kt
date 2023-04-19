package com.pbear.mainfunctionalserver.dev

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class DevRouter {
    @Bean
    fun devRoute() : RouterFunction<ServerResponse> = RouterFunctions.route()
        .GET("/dev") { ServerResponse.ok().bodyValue(mapOf("result" to "success")) }
        .build()
}
package com.pbear.mainfunctionalserver.dev

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

import org.springframework.web.reactive.function.server.RequestPredicates.*


@Configuration
class DevRouter {
    @Bean
    fun devRoute(devHandler: DevHandler) : RouterFunction<ServerResponse> = RouterFunctions
        .nest(
            path("/dev"),
            router {
                GET("") { devHandler.getDev() }
                POST("") { devHandler.postDev(it) }
                GET("/data/{devId}") { devHandler.getData(it) }
                POST("/data") { devHandler.postData(it) }
            })
}
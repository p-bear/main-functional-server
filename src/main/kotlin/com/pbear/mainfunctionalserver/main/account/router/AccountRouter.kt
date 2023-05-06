package com.pbear.mainfunctionalserver.main.account.router

import com.pbear.mainfunctionalserver.main.account.handler.AccountHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class AccountRouter {
    @Bean
    fun routeAccount(accountHandler: AccountHandler): RouterFunction<ServerResponse> = RouterFunctions
        .nest(
            RequestPredicates.path("/main/api/account"),
            router {
                POST("", accountHandler::postAccount)
                PUT("", accountHandler::putAccount)
                DELETE("/{userId}", accountHandler::deleteAccount)
                POST("/password", accountHandler::checkAccountPassword)
            })
}
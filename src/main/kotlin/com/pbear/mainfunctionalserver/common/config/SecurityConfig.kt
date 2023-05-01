package com.pbear.mainfunctionalserver.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain = serverHttpSecurity
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers("/**").permitAll()
        .and()
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(4)
}
package com.pbear.mainfunctionalserver.dev

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class DevHandler(private val devRepository: DevRepository) {
    private val log = KotlinLogging.logger {  }

    fun getDev(): Mono<ServerResponse> = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(mapOf("result" to "success"))
        .doOnNext { log.info("dev GET") }

    fun postDev(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(serverRequest.bodyToMono(HashMap::class.java))
        .doOnNext { log.info("dev POST") }

    fun getData(serverRequest: ServerRequest): Mono<ServerResponse> = this.devRepository
        .findById(serverRequest.pathVariable("devId").toLong())
        .flatMap { ok().bodyValue(it) }
        .switchIfEmpty { badRequest().bodyValue(mapOf("code" to "NOT_EXIST", "resultMessage" to "not exist")) }

    fun postData(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .body(serverRequest
            .bodyToMono(Dev::class.java)
            .flatMap {
                this.devRepository.save(it)
            })

}
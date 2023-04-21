package com.pbear.mainfunctionalserver.main.account.handler

import com.pbear.mainfunctionalserver.main.account.data.dto.PostAccount
import com.pbear.mainfunctionalserver.main.account.data.entity.Account
import com.pbear.mainfunctionalserver.main.account.repository.AccountRepository
import mu.KotlinLogging
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class AccountHandler(private val modelMapper: ModelMapper, private val accountRepository: AccountRepository) {
    private val log = KotlinLogging.logger {  }

    fun postAccount(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .body(serverRequest
            .bodyToMono(PostAccount::class.java)
            .map { this.modelMapper.map(it, Account::class.java) }
            .flatMap { this.accountRepository.save(it) })
}
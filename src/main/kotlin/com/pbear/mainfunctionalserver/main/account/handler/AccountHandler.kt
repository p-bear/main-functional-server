package com.pbear.mainfunctionalserver.main.account.handler

import com.pbear.mainfunctionalserver.common.data.dto.CommonResDTO
import com.pbear.mainfunctionalserver.common.data.exception.CustomException
import com.pbear.mainfunctionalserver.common.data.exception.ResponseErrorCode
import com.pbear.mainfunctionalserver.main.account.data.dto.ReqPostAccount
import com.pbear.mainfunctionalserver.main.account.data.dto.ReqPostAccountPassword
import com.pbear.mainfunctionalserver.main.account.data.dto.ReqPutAccount
import com.pbear.mainfunctionalserver.main.account.data.dto.ResAccount
import com.pbear.mainfunctionalserver.main.account.data.entity.Account
import com.pbear.mainfunctionalserver.main.account.repository.AccountRepository
import mu.KotlinLogging
import org.modelmapper.ModelMapper
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.TransientDataAccessResourceException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class AccountHandler(private val modelMapper: ModelMapper, private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder) {
    private val log = KotlinLogging.logger {  }

    fun checkAccountPassword(serverRequest: ServerRequest): Mono<ServerResponse> = serverRequest
        .bodyToMono(ReqPostAccountPassword::class.java)
        .doOnNext { serverRequest.exchange().attributes["userId"] = it.userId }
        .zipWhen { this.accountRepository.findByUserId(it.userId) }
        .flatMap {
            if (passwordEncoder.matches(it.t1.password, it.t2.password)) {
                ok().bodyValue(this.modelMapper.map(it.t2, ResAccount::class.java))
            } else {
                return@flatMap Mono.error(
                    CustomException(ResponseErrorCode.ACCOUNT_3, null,
                        mapOf("{userId}" to serverRequest.exchange().attributes["userId"] as String))
                )
            }
        }
        .switchIfEmpty { throw CustomException(ResponseErrorCode.ACCOUNT_2, null,
            mapOf("{userId}" to serverRequest.exchange().attributes["userId"] as String)) }

    fun postAccount(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .body(serverRequest
            .bodyToMono(ReqPostAccount::class.java)
            .doOnNext { it.password = this.passwordEncoder.encode(it.password) }
            .map { this.modelMapper.map(it, Account::class.java) }
            .doOnNext { serverRequest.exchange().attributes["userId"] = it.userId }
            .flatMap { this.accountRepository.save(it) }
            .map { CommonResDTO(this.modelMapper.map(it, ResAccount::class.java)) }
            .onErrorMap {
                when (it) {
                    is DataIntegrityViolationException -> CustomException(ResponseErrorCode.ACCOUNT_1, it,
                        mapOf("{userId}" to serverRequest.exchange().attributes["userId"] as String))
                    else -> it
                }
            })

    fun putAccount(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .body(serverRequest
            .bodyToMono(ReqPutAccount::class.java)
            .doOnNext { it.password = passwordEncoder.encode(it.password) }
            .zipWhen { this.accountRepository.findByUserId(it.userId) }
            .doOnNext { this.modelMapper.map(it.t1, it.t2) }
            .map { it.t2 }
            .doOnNext { serverRequest.exchange().attributes["userId"] = it.userId }
            .flatMap { this.accountRepository.save(it) }
            .map { CommonResDTO(this.modelMapper.map(it, ResAccount::class.java)) }
            .onErrorMap {
                when (it) {
                    is TransientDataAccessResourceException -> CustomException(ResponseErrorCode.ACCOUNT_2, it,
                        mapOf("{userId}" to serverRequest.exchange().attributes["userId"] as String))
                    else -> it
                }
            })

    fun deleteAccount(serverRequest: ServerRequest): Mono<ServerResponse> = ok()
        .body(this.accountRepository
            .deleteAccountByUserId(serverRequest.pathVariable("userId"))
            .then(Mono.fromCallable { CommonResDTO(null) }))
}
package com.pbear.mainfunctionalserver.main.account.repository

import com.pbear.mainfunctionalserver.main.account.data.entity.AccountGoogle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AccountGoogleRepository: ReactiveCrudRepository<AccountGoogle, Long> {
    fun findByAccountId(accountId: Long): Mono<AccountGoogle>
    fun deleteByGoogleId(googleId: String): Mono<Void>
}
package com.pbear.mainfunctionalserver.main.account.repository

import com.pbear.mainfunctionalserver.main.account.data.entity.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AccountRepository: ReactiveCrudRepository<Account, Long> {
    fun findByUserId(accountId: String): Mono<Account>

    fun deleteAccountByUserId(accountId: String): Mono<Void>
}
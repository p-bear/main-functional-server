package com.pbear.mainfunctionalserver.main.account.repository

import com.pbear.mainfunctionalserver.main.account.data.entity.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AccountRepository: ReactiveCrudRepository<Account, String>
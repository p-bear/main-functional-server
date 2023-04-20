package com.pbear.mainfunctionalserver.dev

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface DevRepository: ReactiveCrudRepository<Dev, Long>
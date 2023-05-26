package com.pbear.mainfunctionalserver.main.easycalendar

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface CalendarTemplateRepository: ReactiveCrudRepository<CalendarTemplate, Long> {
    fun findAllByAccountId(accountId: Long): Flux<CalendarTemplate>
}
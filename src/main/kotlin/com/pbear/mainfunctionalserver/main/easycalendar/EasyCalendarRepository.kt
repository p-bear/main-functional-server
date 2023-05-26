package com.pbear.mainfunctionalserver.main.easycalendar

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CalendarTemplateRepository: ReactiveCrudRepository<CalendarTemplate, Long>
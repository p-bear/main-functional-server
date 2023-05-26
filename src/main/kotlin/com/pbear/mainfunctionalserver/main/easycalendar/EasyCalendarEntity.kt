package com.pbear.mainfunctionalserver.main.easycalendar

import com.pbear.mainfunctionalserver.common.util.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("calendar_template")
@NoArg
data class CalendarTemplate(
    @Id
    var id: Long? = null,
    val accountId: Long,
    val title: String,
    val summary: String,
    var properties: String? = null,
    @CreatedDate
    var creDate: LocalDateTime? = null,
    @LastModifiedDate
    var modDate: LocalDateTime? = null
)
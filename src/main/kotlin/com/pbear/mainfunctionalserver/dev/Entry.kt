package com.pbear.mainfunctionalserver.dev

import com.pbear.mainfunctionalserver.common.util.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Table("dev")
@NoArg
data class Dev (
    @Id
    val id: Long?,
    var devString: String?,
    var devNumber: Long?,
    var devBoolean: Boolean?,
    @CreatedDate
    var creDate: LocalDateTime?,
    @LastModifiedDate
    var modDate: LocalDateTime?,
    var testLocalDate: LocalDate?,
    var testLocalDateTime: LocalDateTime?)
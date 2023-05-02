package com.pbear.mainfunctionalserver.dev

import com.pbear.mainfunctionalserver.common.util.NoArg
import java.time.LocalDate
import java.time.LocalDateTime

@NoArg
data class PostDevData(
    val id: Long?,
    var devString: String?,
    var devNumber: Long?,
    var devBoolean: Boolean?,
    var testLocalDate: LocalDate?,
    var testLocalDateTime: LocalDateTime?
)

@NoArg
data class PutDevData(
    val id: Long,
    var devString: String?,
    var devNumber: Long?,
    var devBoolean: Boolean?,
    var testLocalDate: LocalDate?,
    var testLocalDateTime: LocalDateTime?
)
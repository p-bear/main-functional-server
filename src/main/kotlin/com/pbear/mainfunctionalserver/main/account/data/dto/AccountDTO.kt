package com.pbear.mainfunctionalserver.main.account.data.dto

import com.pbear.mainfunctionalserver.common.util.NoArg
import java.time.LocalDateTime

data class ReqPostAccount(
    val userId: String,
    var password: String
)

data class ReqPutAccount(
    val userId: String,
    var password: String
)

@NoArg
data class ResAccount(
    val id: Long,
    val userId: String,
    val creDate: LocalDateTime,
    val modDate: LocalDateTime
)
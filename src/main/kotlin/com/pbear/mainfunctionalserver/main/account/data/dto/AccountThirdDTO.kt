package com.pbear.mainfunctionalserver.main.account.data.dto

import com.pbear.mainfunctionalserver.common.util.NoArg
import java.time.LocalDateTime

@NoArg
data class ReqPostAccountGoogle(
    val googleId: String,
    val accountId: Long,
    val email: String,
    val name: String,
    val givenName: String,
    val verifiedEmail: Boolean,
    val scope: String,
    val refreshToken: String
)

@NoArg
data class ResAccountGoogle(
    val id: Long,
    val googleId: String,
    val accountId: Long,
    val email: String,
    val name: String,
    val givenName: String,
    val verifiedEmail: Boolean,
    var creDate: LocalDateTime,
    var modDate: LocalDateTime
)
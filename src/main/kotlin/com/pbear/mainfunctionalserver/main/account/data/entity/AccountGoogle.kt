package com.pbear.mainfunctionalserver.main.account.data.entity

import com.pbear.mainfunctionalserver.common.util.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account_google")
@NoArg
data class AccountGoogle(
    @Id
    val id: Long?,
    val googleId: String,
    val accountId: Long,
    val email: String,
    val name: String,
    val givenName: String,
    val verifiedEmail: Boolean,
    val scope: String,
    val refreshToken: String,
    @CreatedDate
    var creDate: LocalDateTime?,
    @LastModifiedDate
    var modDate: LocalDateTime?
)
package com.pbear.mainfunctionalserver.main.account.data.entity

import com.pbear.mainfunctionalserver.common.util.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
@NoArg
data class Account(
    @Id
    val id: Long?,
    val userId: String,
    val password: String,
    @CreatedDate
    var creDate: LocalDateTime?,
    @LastModifiedDate
    var modDate: LocalDateTime?
)
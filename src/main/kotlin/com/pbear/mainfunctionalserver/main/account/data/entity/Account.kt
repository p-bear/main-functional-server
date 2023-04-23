package com.pbear.mainfunctionalserver.main.account.data.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pbear.mainfunctionalserver.common.util.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
@NoArg
data class Account(
    @Id
    val accountId: String,
    val password: String,
    @CreatedDate
    var creDate: LocalDateTime?,
    @LastModifiedDate
    var modDate: LocalDateTime?
): Persistable<String> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account
        return accountId == other.accountId
    }

    @ReadOnlyProperty
    private var newAccount: Boolean = false
    @JsonIgnore
    override fun isNew(): Boolean {
        return this.newAccount || this.accountId.isEmpty()
    }
    override fun getId():String = this.accountId
    override fun hashCode(): Int = accountId.hashCode()
}
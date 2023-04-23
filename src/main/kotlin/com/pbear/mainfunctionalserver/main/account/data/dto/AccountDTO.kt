package com.pbear.mainfunctionalserver.main.account.data.dto

data class ReqPostAccount(
    val id: String,
    val password: String,
    val newAccount: Boolean = true
)

data class ReqPutAccount(
    val id: String,
    val password: String,
    val newAccount: Boolean = false
)
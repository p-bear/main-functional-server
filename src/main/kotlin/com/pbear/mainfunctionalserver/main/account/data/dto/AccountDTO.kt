package com.pbear.mainfunctionalserver.main.account.data.dto

data class PostAccount(
    val id: String,
    val password: String,
    val newAccount: Boolean = true
)
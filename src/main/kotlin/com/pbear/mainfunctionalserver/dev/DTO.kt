package com.pbear.mainfunctionalserver.dev


data class PostDevData(
    val id: Long?,
    var devString: String?,
    var devNumber: Long?,
    var devBoolean: Boolean?,
)

data class PutDevData(
    val id: Long,
    var devString: String?,
    var devNumber: Long?,
    var devBoolean: Boolean?,
)
package com.suatzengin.data.request.charityscore

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.UUID

@Serializable
data class UpdateScoreRequest(
    val userId: String,
    val point: Long,
){
    @Transient
    val userUUID : UUID = UUID.fromString(userId)
}

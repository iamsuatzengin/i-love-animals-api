package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CharityScoreResponse(
    val id: Int,
    val userId: String,
    val userMail: String,
    val userFullName: String,
    val userImageUrl: String,
    val point: Long
)

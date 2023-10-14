package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class AdCommentResponse(
    val commentId: Long,
    val comment: String,
    val advertisementId: String,
    val userId: String?
)

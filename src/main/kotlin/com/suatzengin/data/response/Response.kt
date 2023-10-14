package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val message: String,
    val status: Boolean
)

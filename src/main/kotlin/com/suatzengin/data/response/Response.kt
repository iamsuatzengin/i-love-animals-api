package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val message: String,
    val status: Boolean
)

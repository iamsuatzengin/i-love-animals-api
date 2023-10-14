package com.suatzengin.data.request.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdRequest(
    val title: String,
    val description: String,
    val category: Int,
    val address: String,
    val isCompleted: Boolean
)

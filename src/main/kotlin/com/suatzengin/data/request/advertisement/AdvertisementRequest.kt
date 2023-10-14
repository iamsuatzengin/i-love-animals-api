package com.suatzengin.data.request.advertisement

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementRequest(
    val title: String,
    val description: String,
    val category: Int,
    val images: Array<String>,
    val longitude: String,
    val latitude: String,
    val address: String,
)

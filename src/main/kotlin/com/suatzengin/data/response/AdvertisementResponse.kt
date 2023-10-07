package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementResponse(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String,
    val category: Int,
    val images: Array<String>,
    val location: Location,
    val isCompleted: Boolean,
    val createdAt: String
)

@Serializable
data class Location(
    val longitude: String,
    val latitude: String,
)